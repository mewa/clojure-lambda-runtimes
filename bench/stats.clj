#!/usr/bin/env clojure

(ns script.stats
  (:require [clojure.string :as s]))

(defn average [seq]
  (/ (reduce + seq) (count seq)))

(defn percentile [p seq]
  (let [cnt (count seq)
        sorted (sort seq)
        splitVal (int (* p cnt))]
    (if (= 1 (mod cnt 2))
      (nth sorted splitVal)

      (/ (+
          (nth sorted splitVal)
          (nth sorted (inc splitVal)))
         2))))

(defn get-numbers [fname]
  (map (comp #(Double/parseDouble %) first)
       (map #(s/split % #" ") (s/split-lines (slurp fname)))))

(defn stats [values]
  {:sum (reduce + values)
   :count (count values)
   :min (reduce min values)
   :max (reduce max values)
   :average (average values)
   :perc10 (percentile 0.1 values)
   :perc25 (percentile 0.25 values)
   :perc75 (percentile 0.75 values)
   :perc90 (percentile 0.9 values)
   :median (percentile 0.5 values)})

  ;; (let [unfilteredAverage (average values)
  ;;       filtered (filter #(< % (* 10 unfilteredAverage)) values)]

(defn print-stats [fname]
  (let [vals (get-numbers fname)
        rawStats (stats vals)
        filtered (filter #(< % (:perc90 rawStats)) vals)
        filteredStats (stats filtered)]
    (println "==>" fname "stats")
    (println "Raw stats: " rawStats)
    (println "Filtered stats: " filteredStats)))

(doseq [fname *command-line-args*]
  (print-stats fname))

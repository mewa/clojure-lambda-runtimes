(ns app.jvm
  (:gen-class
   :methods [^:static [handleRequest [String] int]])
  (:require [app.core :as core]))

(defn -handleRequest
  [s]
  (time
   (let [cnt (count (app.core/core-test 5000))]
     (println cnt)
     cnt)))

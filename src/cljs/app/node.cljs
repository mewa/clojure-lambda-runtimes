(ns app.node
  (:require [app.core :as core]))

(enable-console-print!)

(defn run [ev ctx callback]
  (callback nil (time
                 (let [cnt (count (app.core/core-test 1000))]
                   (println cnt)
                   cnt))))

(set! (.-exports js/module) (clj->js {:run run}))

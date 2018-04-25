(ns app.node)

(enable-console-print!)

(defn run [ev ctx callback]
  (callback nil (time
                 (str "I shout out loud: " ev "!"))))

(set! (.-exports js/module) (clj->js {:run run}))

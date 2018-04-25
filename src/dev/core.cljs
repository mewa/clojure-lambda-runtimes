(ns ^:figwheel-always figwheel4node-server.core
  (:require [cljs.nodejs :as nodejs]
            [app.node :as node]))
(nodejs/enable-util-print!)

(println "Hello from the Node!")

(*main-cli-fn*)

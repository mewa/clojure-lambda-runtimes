(defproject app "0.1.0-SNAPSHOT"
  :description "Comparison of different runtimes"
  :url "http://marcinchmiel.com"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [[org.clojure/clojure "1.8.0"]
                 [org.clojure/clojurescript "1.10.238"]
                 [org.clojure/core.async "0.4.474"]
                 [cider/piggieback "0.3.1"]
                 [figwheel-sidecar "0.5.16-SNAPSHOT"]]
  :repl-options {:nrepl-middleware [cider.piggieback/wrap-cljs-repl]}
  :plugins [[lein-cljsbuild "1.1.7"]
            [lein-figwheel "0.5.16-SNAPSHOT"]]
  :source-paths ["src/cljc", "src/clj"]
  :profiles {:heavy {:aot [app.jvm]}
             :light {:aot [app.jvmlight]}}
  :cljsbuild {:builds [{:id "dev"
                        :source-paths ["src/dev", "src/cljs", "src/cljc"]
                        :figwheel true
                        :compiler {
                                   :output-to "target/server/figwheel_node_server.js"
                                   :output-dir "target/server"
                                   :main figwheel4node-server.core
                                   :target :nodejs
                                   :optimizations :none
                                   }},
                       {:id "min"
                        :source-paths ["src/cljs", "src/cljc"]
                        :compiler {
                                   :output-dir "target/js"
                                   :output-to "target/js/cljstest.js"
                                   :main app.node
                                   :target :nodejs
                                   :optimizations :simple
                                   }}]}
  :figwheel {})

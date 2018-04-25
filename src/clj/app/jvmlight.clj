(ns app.jvmlight
  (:gen-class
   :methods [^:static [handleRequest [String] String]]))

(defn -handleRequest
  [s]
  (time
   (str "I shout out loud: " s "!")))

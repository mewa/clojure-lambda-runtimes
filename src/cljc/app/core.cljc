(ns app.core)

(defn core-test
  [n]
  (letfn [(accumulate [n acc]
            (if (> n 0)
              (let [ascii (range 32 127)
                    symbols (map char(take n ascii))]
                (recur (dec n) (str acc (apply str symbols))))
              acc))]
    (accumulate n "")))

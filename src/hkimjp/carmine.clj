(ns hkimjp.carmine
  (:refer-clojure :exclude [set get keys])
  (:require
   [environ.core :refer [env]]
   [taoensso.carmine :as car :refer [wcar]]
   [taoensso.telemere :as t]))

(defmacro wcar* [& body] `(wcar my-wcar-opts ~@body))

(defonce my-conn-pool (car/connection-pool {}))
(def my-conn-spec {:uri (or (env :redis) "redis://localhost:6379")})
(def my-wcar-opts {:pool my-conn-pool :spec my-conn-spec})



;; no use. backward compatibility
(defn create-conn
  ([] (create-conn (or (env :redis) "redis://localhost:6379")))
  ([uri]
   (t/log! {:level :debug :id "create-conn" :msg uri})
   (try
     (alter-var-root #'my-conn-pool (constantly (car/connection-pool {})))
     (alter-var-root #'my-conn-spec (constantly {:uri uri}))
     (alter-var-root #'my-wcar-opts
                     (constantly {:pool my-conn-pool :spec my-conn-spec}))
     (wcar* #_{:clj-kondo/ignore [:unresolved-var]}
      (car/ping))
     (catch Exception e
       (let [msg (format "%s: %s" uri (.getMessage e))]
         (t/log! {:level :fatal :msg msg})
         (throw (Exception. msg))
         (System/exit 0))))))

; no use. backward compatibility
(defn close-conn []
  (t/log! {:level :debug :id "close-conn"})
  (alter-var-root #'my-conn-pool (constantly nil))
  (alter-var-root #'my-conn-spec (constantly nil))
  (alter-var-root #'my-wcar-opts (constantly nil)))

;; live or dead
(defn ping []
  (t/log! {:level :debug :msg "ping"})
  (wcar* #_{:clj-kondo/ignore [:unresolved-var]}
   (car/ping)))

;; String
(defn set [key value]
  (t/log! {:leve :debug :msg (str "set " key " " value)})
  (wcar* #_{:clj-kondo/ignore [:unresolved-var]}
   (car/set key value)))

(defn get [key]
  (t/log! {:level :debug :msg (str "get " key)})
  (wcar* #_{:clj-kondo/ignore [:unresolved-var]}
   (car/get key)))

#_{:clojure-lsp/ignore [:clojure-lsp/unused-public-var]}
(defn incr [counter]
  (t/log! {:level :debug :msg "incr"})
  (wcar* #_{:clj-kondo/ignore [:unresolved-var]}
   (car/incr counter)))

#_{:clojure-lsp/ignore [:clojure-lsp/unused-public-var]}
(defn decr [counter]
  (t/log! {:level :debug :msg "decr"})
  (wcar* #_{:clj-kondo/ignore [:unresolved-var]}
   (car/decr counter)))

(defn del [key]
  (t/log! {:level :debug :msg (str "del " key)})
  (wcar* #_{:clj-kondo/ignore [:unresolved-var]}
   (car/del key)))

#_{:clojure-lsp/ignore [:clojure-lsp/unused-public-var]}
(defn exist? [key]
  (some? (get key)))

#_{:clojure-lsp/ignore [:clojure-lsp/unused-public-var]}
(defn exists [key]
  (t/log! {:level :debug :msg (str "exists " key)})
  (wcar* #_{:clj-kondo/ignore [:unresolved-var]}
   (car/exists key)))

(defn keys [key]
  (t/log! {:level :debug :msg (str "keys " key)})
  (wcar* #_{:clj-kondo/ignore [:unresolved-var]}
   (car/keys key)))

(defn scan
  ([cursor pattern]
   (t/log! {:level :debug :id "scan"
            :data {:cursor cursor :pattern pattern}})
   (wcar* #_{:clj-kondo/ignore [:unresolved-var]}
    (car/scan cursor "MATCH" pattern)))
  ([cursor pattern count]
   (t/log! {:level :debug :id "scan" :data {:cursor cursor :pattern pattern :count count}})
   (wcar* #_{:clj-kondo/ignore [:unresolved-var]}
    (car/scan cursor "MATCH" pattern "COUNT" count))))

; the function name `scan0` is for backward compatibility.
(defn scan0
  ([pattern] (scan0 pattern 100))
  ([pattern count]
   (t/log! {:level :debug :id "scan0" :data {:pattern pattern :count count}})
   (loop [cursor 0 result []]
     (let [[c r] (scan cursor pattern count)
           n (parse-long c)
           result (concat result r)]
       (if (zero? n)
         result
         (recur n result))))))

(def scan-all scan0)

;; expiration
#_{:clojure-lsp/ignore [:clojure-lsp/unused-public-var]}
(defn setex [key expire value]
  (t/log! {:level :debug :msg (str "setex " key " " expire " " value)})
  (wcar* #_{:clj-kondo/ignore [:unresolved-var]}
   (car/setex key expire value)))

#_{:clojure-lsp/ignore [:clojure-lsp/unused-public-var]}
(defn expire [key value]
  (t/log! {:level :debug :msg (str "expire " key " " value)})
  (wcar* #_{:clj-kondo/ignore [:unresolved-var]}
   (car/expire key value)))

#_{:clojure-lsp/ignore [:clojure-lsp/unused-public-var]}
(defn ttl [key]
  (t/log! {:level :debug :msg (str "ttl " key)})
  (wcar* #_{:clj-kondo/ignore [:unresolved-var]}
   (car/ttl key)))

;; Lists
#_{:clojure-lsp/ignore [:clojure-lsp/unused-public-var]}
(defn lpush [key element]
  (t/log! {:level :debug :msg (str "lpush " key " " element)})
  (wcar* #_{:clj-kondo/ignore [:unresolved-var]}
   (car/lpush key element)))

#_{:clojure-lsp/ignore [:clojure-lsp/unused-public-var]}
(defn lrange
  ([key] (lrange key 0 -1))
  ([key start stop]
   (t/log! {:level :debug :msg (str "lrange " key " " start " " stop)})
   (wcar* #_{:clj-kondo/ignore [:unresolved-var]}
    (car/lrange key start stop))))

#_{:clojure-lsp/ignore [:clojure-lsp/unused-public-var]}
(defn llen [key]
  (t/log! {:level :debug :msg (str "llen " key)})
  (wcar* #_{:clj-kondo/ignore [:unresolved-var]}
   (car/llen key)))

;; Sets
(defn sadd
  ([key element]
   (t/log! {:level :debug :id "sadd" :data {:key key :element element}})
   (wcar* #_{:clj-kondo/ignore [:unresolved-var]}
    (car/sadd key element)))
  ([key element & elements]
   (t/log! {:level :debug :id "sadd" :data {:key key :element element}})
   (wcar* #_{:clj-kondo/ignore [:unresolved-var]}
    (apply car/sadd key (cons element elements)))))

(defn smembers [key]
  (t/log! {:level :debug :id "smembers" :data {:key key}})
  (wcar* #_{:clj-kondo/ignore [:unresolved-var]}
   (car/smembers key)))

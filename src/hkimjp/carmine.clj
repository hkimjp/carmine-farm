(ns hkimjp.carmine
  (:refer-clojure :exclude [set get keys])
  (:require
   [environ.core :refer [env]]
   [taoensso.carmine :as car]
   [taoensso.telemere :as t]))

(defmacro wcar* [& body] `(car/wcar my-wcar-opts ~@body))
(def my-conn-pool nil)
(def my-conn-spec nil)
(def my-wcar-opts nil)

(defn create-conn
  ([] (create-conn (or (env :redis) "redis://localhost:6379")))
  ([uri]
   (t/log! {:level :info :id "create-conn" :msg uri})
   (try
     (alter-var-root #'my-conn-pool (constantly (car/connection-pool {})))
     (alter-var-root #'my-conn-spec (constantly {:uri uri}))
     (alter-var-root #'my-wcar-opts
                     (constantly {:pool my-conn-pool :spec my-conn-spec}))
     (wcar* (car/ping))
     (catch Exception e
       (let [msg (format "%s: %s" uri (.getMessage e))]
         (t/log! {:level :fatal :msg msg})
         (throw (Exception. msg))
         (System/exit 0))))))

(def redis-server create-conn)

; FIXME: properly closed by this?
(defn close-conn []
  (t/log! {:level :info :id "close-conn"})
  (alter-var-root #'my-conn-pool (constantly nil))
  (alter-var-root #'my-conn-spec (constantly nil))
  (alter-var-root #'my-wcar-opts (constantly nil)))

;; life or death
(defn ping []
  (t/log! {:level :debug :msg "ping"})
  (wcar* (car/ping)))

;; String
(defn set [key value]
  (t/log! {:leve :debug :msg (str "set " key " " value)})
  (wcar* (car/set key value)))

(defn get [key]
  (t/log! {:level :debug :msg (str "get " key)})
  (wcar* (car/get key)))

(defn incr [counter]
  (t/log! {:level :debug :msg "incr"})
  (wcar* (car/incr counter)))

(defn decr [counter]
  (t/log! {:level :debug :msg "decr"})
  (wcar* (car/decr counter)))

(defn del [key]
  (t/log! {:level :debug :msg (str "del " key)})
  (wcar* (car/del key)))

(defn exist? [key]
  (some? (get key)))

(defn exists [key]
  (t/log! {:level :debug :msg (str "exists " key)})
  (wcar* (car/exists key)))

(defn keys [key]
  (t/log! {:level :debug :msg (str "keys " key)})
  (wcar* (car/keys key)))

(defn scan
  ([cursor pattern]
   (wcar* (car/scan cursor "MATCH" pattern)))
  ([cursor pattern count]
   (wcar* (car/scan cursor "MATCH" pattern "COUNT" count))))

; remains for backward compatibility
; rename to `scan-all`?
(defn scan0 [pattern]
  (loop [cursor 0 result []]
    (let [[c r] (scan cursor pattern)
          n (parse-long c)
          result (concat result r)]
      (if (zero? n)
        result
        (recur n result)))))

(def scan-all scan0)

;; expiration
(defn setex [key expire value]
  (t/log! {:level :debug :msg (str "setex " key " " expire " " value)})
  (wcar* (car/setex key expire value)))

(defn expire [key value]
  (t/log! {:level :debug :msg (str "expire " key " " value)})
  (wcar* (car/expire key value)))

(defn ttl [key]
  (t/log! {:level :debug :msg (str "ttl " key)})
  (wcar* (car/ttl key)))

;; Lists
(defn lpush [key element]
  (t/log! {:level :debug :msg (str "lpush " key " " element)})
  (wcar* (car/lpush key element)))

(defn lrange
  ([key] (lrange key 0 -1))
  ([key start stop]
   (t/log! {:level :debug :msg (str "lrange " key " " start " " stop)})
   (wcar* (car/lrange key start stop))))

(defn llen [key]
  (t/log! {:level :debug :msg (str "llen " key)})
  (wcar* (car/llen key)))

;; Sets
(defn sadd
  ([key element]
   (wcar* (car/sadd key element)))
  ([key element & elements]
   (wcar* (apply car/sadd key (cons element elements)))))

(defn smembers [key]
  (wcar* (car/smembers key)))

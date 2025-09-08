(ns user
  (:require
   [hkimjp.carmine :as c]
   [taoensso.telemere :as t]))

(t/set-min-level! :debug)
(c/redis-server)

(comment
  (c/redis-server)
  (c/ping)
  (c/set "x" 100)
  (c/get "x")
  (c/incr "x")
  (c/expire "x" 10)
  (c/ttl "x")

  :rcf)

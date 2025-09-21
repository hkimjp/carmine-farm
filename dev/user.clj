(ns user
  (:require
   [hkimjp.carmine :as c]
   [taoensso.telemere :as t]))

(t/set-min-level! :debug)
(c/create-conn)

(comment
  (c/create-conn)
  (c/close-conn)
  (c/set "x" 100)
  (c/get "x")
  (c/incr "x")
  (c/expire "x" 10)
  (c/ttl "x")
  (c/close-conn)
  :rcf)

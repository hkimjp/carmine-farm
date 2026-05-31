(ns user
  (:require
   [hkimjp.carmine :as c]
   [taoensso.telemere :as tel]))

(tel/set-min-level! :debug)

(comment
  (c/sadd "mem1" "1")
  (c/smembers "mem1")

  (c/sadd "mem2" 1 2 3)
  (c/smembers "mem2")

  (c/create-conn)
  (c/ping)
  (c/keys "*")
  (count (c/keys "*"))
  (c/scan0 "commented:*")
  (c/scan-all "commented:*")

  (c/set "a" 1)
  (c/get "a")
  (c/del "a")
  (c/get "a")

  (c/close-conn)
  :rcf)

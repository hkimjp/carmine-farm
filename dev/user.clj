(ns user
  (:require
   [hkimjp.carmine :as c]
   [taoensso.telemere :as tel]))

(tel/set-min-level! :debug)

(comment
  (c/create-conn)

  (time (c/scan-all "track*" 10))
  "Elapsed time: 77.830417 msecs"
  (time (c/scan-all "track*"))
  "Elapsed time: 20.248375 msecs"
  (time (c/scan-all "track*" 1000))
  "Elapsed time: 7.350041 msecs"
  (time (c/scan-all "track*" 10000))
  "Elapsed time: 4.830417 msecs"

  (c/sadd "mem1" "1")
  (c/smembers "mem1")

  (c/sadd "mem2" 1 2 3)
  (c/smembers "mem2")

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

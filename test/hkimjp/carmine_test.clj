(ns hkimjp.carmine-test
  (:require
   [clojure.test :refer [deftest testing is]]
   [hkimjp.carmine :as c]))

(deftest ping-test
  (testing "ping"
    (is (= (c/ping) "PONG"))))

(deftest a-test
  (testing "FIXED"
    (is (= 0 0))))

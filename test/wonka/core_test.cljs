(ns wonka.core-test
  (:require [cljs.test :refer-macros [deftest is testing run-tests]]
            [wonka.core :as w]))

(w/init {:theme {:theme.colors/pink "cyan"}
         :theme-prefix "theme"})

(w/css {:margin 10})
        ;;:color :theme.colors/pink})

(deftest testing
  (testing "they are equal"
    (is (= "yea" "yea"))))

(comment
  (enable-console-print!)
  (run-tests))

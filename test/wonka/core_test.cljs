(ns wonka.core-test
  (:require [cljs.test :as t :refer-macros [deftest is testing]]
            [wonka.helpers.dom :as dom]
            [wonka.core :as w :refer [css]]))

(t/use-fixtures :once
  {:before dom/create-test-container!
   :after  dom/remove-test-container!})

(deftest css-test
  (testing "a plain CSS object works"
    (let [$ (css {:font-weight 700
                  :color       "rgb(10, 20, 30)"})
          p (dom/->props $ #{:font-weight :color})]
      (is (= p {:font-weight "700"
                :color       "rgb(10, 20, 30)"}))))

  (testing "multiple CSS maps work"
    (let [$ (css {:font-weight 700
                  :color       "rgb(100, 50, 75)"}
                 {:font-style  :italic
                  :font-weight 900})
          p (dom/->props $ #{:font-weight :color :font-style})]
      (is (= p {:font-weight "900"
                :color       "rgb(100, 50, 75)"
                :font-style  "italic"}))))

  (testing "inheritance works"
    (let [$inherit (css {:display "flex"})
          $        (css {:font-weight 700
                         :color       "rgb(100, 50, 75)"}
                        $inherit
                        {:font-style "italic"})
          p        (dom/->props $ #{:font-style :font-weight :color :display})]
      (is (= p {:font-weight "700"
                :font-style  "italic"
                :color       "rgb(100, 50, 75)"
                :display     "flex"}))))

  (testing "keywords/strings work"
    (let [$ (css :truncated
                 {:font-weight 700
                  :color       "rgb(100, 50, 75)"}
                 "something-else")
          c (dom/->classes $)]
      (is (re-matches #"^css\-\w+ truncated something-else$" c))))

  (testing "nesting works"
    (let [$        (css
                    :truncated
                    {:font-weight 700
                     :color       "rgb(100, 50, 75)"})
          $wrapped (css $ {:font-weight 900
                           :display     "flex"})
          p        (dom/->props $wrapped #{:font-weight :color :display})
          c        (dom/->classes $wrapped)]
      (is (= p {:font-weight "900"
                :color       "rgb(100, 50, 75)"
                :display     "flex"}))
      (is (re-matches #"^css\-\w+ truncated$" c)))))

(comment
  (enable-console-print!)
  (t/run-tests))

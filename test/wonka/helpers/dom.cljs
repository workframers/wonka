(ns wonka.helpers.dom
  (:require [wonka.helpers.enzyme :as z]
            [wonka.core :as p :refer [css]]))

(defn- filter-props [csd props]
  (let [filtered (reduce
                   (fn [m v]
                     (assoc m v (.getPropertyValue csd (name v))))
                   {} props)]
    filtered))

(defn test-container []
  (.getElementById js/document "test-container"))

(defn with-mount! [style fn]
  (let [cmp [:div {:class style}]
        c   (z/mount cmp {:attachTo (test-container)})
        res (fn c)]
    (z/unmount c)
    res))

(defn ->props [style props]
  (with-mount! style
    (fn [c]
      (let [csd (->> (.getDOMNode c)
                     (.getComputedStyle js/window))]
        (filter-props csd props)))))

(defn ->classes [style]
  (with-mount! style
    (fn [c]
      (-> (.getDOMNode c)
          (.getAttribute "class")))))

(defn create-test-container! []
  (let [el (doto (.createElement js/document "div")
                 (.setAttribute "id" "test-container"))]
    (-> (.-body js/document)
        (.appendChild el))))

(defn remove-test-container! []
  (-> (test-container) (.remove)))


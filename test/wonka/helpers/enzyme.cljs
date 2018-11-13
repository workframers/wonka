(ns wonka.helpers.enzyme
  (:require [reagent.core :as r]
            [oops.core :refer [oget]]
            ["enzyme-adapter-react-16" :as Adapter]
            ["enzyme" :as enzyme]))

(.configure enzyme #js {:adapter (Adapter.)})

(defn mount [c & [opts]]
  (.mount enzyme (r/as-element c) (some-> opts clj->js)))

(defn unmount [c]
  (.unmount c))

(defn render [c]
  (.render enzyme (r/as-element c)))

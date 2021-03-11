(ns rpg-services.database.city-encounters
  (:require [monger.core :as mg]
            [monger.collection :as mc]
            [monger.conversion :as mgcon]
            [monger.operators :refer :all]
            [rpg-services.database :refer [db]]
            [clojure.spec.alpha :as spec]))

(def SIZE_VALUES ["Hamlet" "Village" "Town" "City" "Metropolis"])
(def OUTCOME_VALUES ["Good" "Neutral" "Bad" "Combat" "Roleplay" "Hook"])


(defn check-all-vals-exist? [valid-vals vals]
  "checks if a given vector's values are all present is a 2nd vector"
  (reduce (fn [acc val]
            (if (and (some #(= val %) valid-vals) acc) ; acc is true and val is presnet
              true
              false)) true vals))

(spec/def ::name string?)
(spec/def ::description string?)
(spec/def ::size (partial check-all-vals-exist? SIZE_VALUES))
(spec/def ::outcomes (partial check-all-vals-exist? OUTCOME_VALUES))

(spec/def ::city_encounter (spec/keys :req-un [::name ::description ::size ::outcomes]))

; e.x
; (spec/valid? ::city_encounter {:name "test" :description "test-desc" :size ["Hamlet" "Village"] :outcomes ["Good"]})
; END SPECS


(defn add-encounter [encounter]
  {:pre [(spec/valid? ::city_encounter encounter)]}
  (let [new-encounter (mc/insert-and-return db "city-encounters" encounter)]
    (println new-encounter)))

(defn get-random-encounter [select-map]
  (let [encounters (mc/find-maps db "city-encounters" select-map)]
    (println (count encounters))
    (if (> (count encounters) 0) ; an empty list is still true
      (rand-nth encounters)
      "No Encounters Found")))




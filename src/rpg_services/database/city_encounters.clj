(ns rpg-services.database.city-encounters
  (:require [monger.core :as mg]
            [monger.collection :as mc]
            [monger.conversion :as mgcon]
            [monger.operators :refer :all]
            [rpg-services.database :refer [db]]
            [clojure.spec.alpha :as spec]))

(spec/def ::size #{"Hamlet" "Village" "Town" "City" "Metropolis"})
(spec/def ::outcomes #{"Good" "Bad" "Combat" "Roleplay" "Hook"})
(spec/def ::type #{"Regular" "Fantastical"})

(spec/def ::city_encounter (spec/keys :req-un [::size ::outcomes ::type]))

; e.x
; (spec/valid? ::city_encounter {:size "Hamlet" :outcomes "Good" :type "Regular"})

; name description size outcome type
(defn add-encounter [encounter]
  {:pre [(spec/valid? ::city_encounter encounter)]}
  (let [new-encounter (mc/insert-and-return db "city-encounters" encounter)]
    (println new-encounter)))

(defn get-random-encounter [select-map]
  (let [encounters (mc/find-maps db "city-encounters" select-map)]
    (rand-nth encounters)))




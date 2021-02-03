(ns notes.ui.sidebar
  (:require [reagent.core :as r]
            [reagent.ratom :as ratom]
            [notes.state :refer [app]]
            [notes.command :refer [dispatch!]]
            [notes.ui.common :refer [link]]))

(defn created-at-sorter [a b]
  (> (:created-at a)
     (:created-at b)))

(defn notes-list []
  (let [notes (r/cursor app [:data :notes])
        notes-list (ratom/make-reaction
                    #(->> @notes
                          (vals)
                          (sort created-at-sorter)))]
    (dispatch! :notes/get-notes)
    (fn []
      [:nav
       [:ul.notes-list
        (for [note @notes-list
              :let [{:keys [id title]} note]]
          ^{:key id}
          [:li [link title [:edit-note {:note-id id}]]])]])))

(defn sidebar []
  [:nav.sidebar
   [notes-list]])

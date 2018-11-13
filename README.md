![](https://raw.githubusercontent.com/workframers/wonka/master/resources/wonka.jpg)

[![Clojars Project](https://img.shields.io/clojars/v/com.workframe/wonka.svg)](https://clojars.org/com.workframe/wonka)

`[com.workframe/wonka "0.1.0-SNAPSHOT"]`

"Ugh, I have to add CSS to my project, what do I need again?"

Just install Wonka and go, even gives you a browser reset :)

### In a nutshell
```clojure
(ns my.app
  (:require [wonka.core :as w]))

;; Easily inject a browser reset (Eric Meyer's modest reset)
;; Do this somewhere once
(w/insert-reset!)

;; Set up theme variables
;; Keywords in this map can be used as CSS values, and their values will be swapped in
;; Wonka recognizes your theme variables by a configured prefix (below), in this case it would be "app"
(def app-theme
  {:app.colors/black "#323232"
   :app.padding/page-wrap 20})
 
 ;; One time set up, give wonka your theme map and prefix
(w/config {:theme app-theme
           :theme-prefix "app"})
           
(def app-root [{:keys [show-border?]}]
  ;; You'll mostly use `w/css` It can take multiple maps and results of other `w/css` calls
  ;; :keyword values are converted to strings
  ;; For CSS rules that take multiple values, use `w/join` e.g. `:box-shadow (w/join 4 4 20 :app.colors/black)`
  [:div {:class (w/css {:display :flex
                        :padding :app.padding/page-wrap
                        :color :app.colors/black}
                       (when show-border?
                         {:border (w/join 1 :solid :app.colors/black)}))}
    "Hello World!"])
```

## License

Copyright © 2018 Workframe, Inc.

Distributed under the Apache License, Version 2.0.

![](https://raw.githubusercontent.com/workframers/wonka/master/resources/wonka.jpg)

[![Clojars Project](https://img.shields.io/clojars/v/com.workframe/wonka.svg)](https://clojars.org/com.workframe/wonka)

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

### API Overview

#### `wonka.core/css`

Takes a variable number of arguments, which could be:

- A map of CSS properties
- A glamor object generated by a previous `css` call
- A CSS class name as a string or keyword
- A `nil` value (so we can use `when`s)

and returns a glamor object or a coll that includes a glamor object and any
CSS class names as strings."

#### `wonka.core/keyframes`

Creates and returns a glamor keyframe definition. Use it inside css:

```clojure
(def kf (w/keyframes {:from {:opacity 0}
                      :to   {:opacity 1}}))

[:div {:class (w/css {:animation kf})}]
```

#### `wonka.core/join`

CSS allows you to specify many shorthand properties as a single string
(ex: `background-image: url(images/bg.gif); background-repeat: no-repeat;`
becomes `background: url(images/bg.gif) no-repeat`). String concat isn't fun
in Clojure, so you can use the paper.core/join to a sequence of properties
into CSS shorthand string. Automatically appends \"px\" to integers and does
paper namespaced keyword lookups in the styleguide.

Ex:

```clojure
[:div {:class (w/css {:background (w/join \"url(images/bg.gif)\" \"no-repeat\")})}]
```

#### `wonka.core/config`

Takes a map with two keywords:

- `:theme` A map of aliased values that can be used as inside of a `w/css` call
- `:theme-prefix` Wonka looks up CSS keyword values as potential theme values

The theme map should have some sort of theme prefix.

Ex:

```clojure
(def app-theme
  {:app.colors/black "#323232"
   :app.fonts/primary "Monotype"})
```

You would pass the string `"app"` as the `:theme-prefix` value.

#### `wonka.core/insert-reset!`

Can be called to add a CSS reset to your app. It's the Eric Meyer reset. If you want something larger, like normalize.css, you can pass any raw CSS string to `wonka.core/insert!`.

#### `wonka.core/insert-global!`

Adds some css to the element globally

Takes a CSS selector string, and a map of CSS

Ex:

```clojure
(wonka.core/insert-global! "#app" {:margin 20})
```

#### `wonka.core/insert!`

Adds some arbitrary CSS to the page

Takes a raw CSS string

Ex:

```clojure
(wonka.core/insert! "body {font-family: Helvetica}")
```



## License

Copyright © 2018 Workframe, Inc.

Distributed under the Apache License, Version 2.0.

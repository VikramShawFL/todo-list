(defproject todo-list "0.1.0-SNAPSHOT"
  :description "A simple webapp using Ring"
  :url "http://example.com/FIXME"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [[org.clojure/clojure "1.6.0"]
                 [ring "1.4.0-beta2"]
                 [compojure "1.3.4"]
                 [hiccup "1.0.5"]
                ]
  :main todo-list.core
  :profiles {:dev
              {:main todo-list.core/-dev-main}
            }
)
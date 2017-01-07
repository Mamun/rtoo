(defproject rscrap "0.1.0-SNAPSHOT"

  :description "FIXME: write description"
  :url "http://example.com/FIXME"
  :license {:name "Eclipse Public License"
            :url  "http://www.eclipse.org/legal/epl-v10.html"}

  :dependencies [[org.clojure/clojure "1.9.0-alpha10"]

                 ;Rule engine deps
                 ;  [org.toomuchcode/clara-rules "0.11.1"]

                 ;;Db deps
                 ;   [c3p0/c3p0 "0.9.1.2"]
                 ; [com.h2database/h2 "1.3.154"]

                 ;  [dosql/core "0.1.0-alpha-SNAPSHOT"]
                 ; [dosql/http-service "0.1.0-SNAPSHOT"]

                 [clj-http "3.1.0"]


                 ;;Web application deps
                 [ring "1.4.0"
                  :exclusions [ring/ring-jetty-adapter]]
                 [ring/ring-defaults "0.2.0"]
                 [ring.middleware.logger "0.5.0"]
                 [compojure "1.5.1"]
                 [selmer "1.0.4"]  ;; html template
                 [enlive "1.1.6"]
                 [environ "1.0.3"]
                 [hiccup "1.0.5"]

                 [ring-webjars "0.1.1"]
                 [org.webjars/bootstrap "3.3.5"]
                 ;[org.webjars/material-design-lite "1.1.1"]
                 [org.immutant/web "2.1.3"                  ;; default Web server
                  :exclusions [ch.qos.logback/logback-core
                               org.slf4j/slf4j-api]]
                 [ch.qos.logback/logback-classic "1.1.3"]

                 ]

  :plugins [[lein-environ "1.0.1"]]

  :min-lein-version "2.5.3"
  :source-paths ["src/clj" "dev"]
  :test-paths ["test/clj"]
  :clean-targets ^{:protect false} [:target-path :compile-path "resources/public/js"]
  :uberjar-name "web-app.jar"

  ;; Use `lein run` if you just want to start a HTTP server, without figwheel
  :main app.server
  :repl-options {:init-ns user}



  :doo {:build "test"}
  :profiles {:dev
             {:dependencies [;[com.stuartsierra/component "0.3.0" :scope "test"]

                             [org.clojure/tools.nrepl "0.2.12"]]
              :repl-options {:port 4555}
              :plugins      [[lein-doo "0.1.6"]]
              }
             :uberjar
             {:source-paths ^:replace ["src/clj"]
              ;:hooks        [leiningen.cljsbuild]
              :omit-source  true
              :aot          :all
              }}


  )

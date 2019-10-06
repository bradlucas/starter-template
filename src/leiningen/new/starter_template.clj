(ns leiningen.new.starter-template
  (:require [leiningen.new.templates :refer [renderer name-to-path ->files]]
            [leiningen.core.main :as main]))

(def render (renderer "starter-template"))

(defn starter-template
  "FIXME: write documentation"
  [name]
  (let [data {:name name
              :sanitized (name-to-path name)}]
    (main/info "Generating fresh 'lein new' starter-template project.")
    (->files data
             [".gitignore"    (render "gitignore")]
             ["LICENSE"       (render "LICENSE")]
             ["README.md"     (render "README.md" data)]
             ["project.clj"   (render "project.clj" data)]

             ["doc/intro.md"  (render "doc/intro.md" data)]

             ;; resources
             ["resources/public/css/bootswatch-lux.min.css"  (render "resources/public/css/bootswatch-lux.min.css")]
             ["resources/public/css/custom.min.css"          (render "resources/public/css/custom.min.css")]
             ["resources/public/css/sticky-footer.css"       (render "resources/public/css/sticky-footer.css")]

             ["resources/public/js/bootstrap-datepicker.js"  (render "resources/public/js/bootstrap-datepicker.js")]
             ["resources/public/js/bootstrap.min.js"         (render "resources/public/js/bootstrap.min.js")]
             ["resources/public/js/custom.js"                (render "resources/public/js/custom.js")]
             ["resources/public/js/jquery.min.js"            (render "resources/public/js/jquery.min.js")]
             ["resources/public/js/popper.min.js"            (render "resources/public/js/popper.min.js")]

             ["resources/sql/create-tables.sql" (render "resources/sql/create-tables.sql")]
             ["resources/sql/queries.sql"       (render "resources/sql/queries.sql")]
             ["resources/sql/test-data.sql"     (render "resources/sql/test-data.sql")]


             ["resources/templates/base.html"                      (render "resources/templates/base.html" data)]
             ["resources/templates/account/profile.html"           (render "resources/templates/account/profile.html")]
             ["resources/templates/auth/login-error-email.html"    (render "resources/templates/auth/login-error-email.html")]
             ["resources/templates/auth/login-error-password.html" (render "resources/templates/auth/login-error-password.html")]
             ["resources/templates/auth/login.html"                (render "resources/templates/auth/login.html")]
             ["resources/templates/static/about.html"              (render "resources/templates/static/about.html" data)]
             ["resources/templates/static/error.html"              (render "resources/templates/static/error.html")]
             ["resources/templates/static/index.html"              (render "resources/templates/static/index.html" data)]
             

             ;; scripts
             ["scripts/rebuild-db.sh" (render "scripts/rebuild-db.sh" data)]
             ["scripts/test-data.sh" (render "scripts/test-data.sh" data)]

             ["src/{{sanitized}}/config.clj" (render "src/starter/config.clj" data)]
             ["src/{{sanitized}}/core.clj" (render "src/starter/core.clj" data)]
             ["src/{{sanitized}}/db.clj" (render "src/starter/db.clj" data)]
             ["src/{{sanitized}}/handler.clj" (render "src/starter/handler.clj" data)]
             ["src/{{sanitized}}/middleware.clj" (render "src/starter/middleware.clj" data)]
             ["src/{{sanitized}}/routes/account.clj" (render "src/starter/routes/account.clj" data)]
             ["src/{{sanitized}}/routes/auth.clj" (render "src/starter/routes/auth.clj" data)]
             ["src/{{sanitized}}/routes/static.clj" (render "src/starter/routes/static.clj" data)]
             ["src/{{sanitized}}/template.clj" (render "src/starter/template.clj" data)]



             ["test/{{sanitized}}/core_test.clj" (render "test/starter/core_test.clj" data)]

)))

# {{name}}

A {{name}} Clojure application with cmdline, compojure, selmer, bootstrap, mount, postgres and buddy auth.


## Configuration

This project reads from a config file  which it looks in ~/.{{name}} with a name of config.edn.

Three values are expected. Set them as you desire. See `config.clj` for more details.

Here is an example.

```
{:root-url "http://localhost:4004"
 :admin-email "you@your-site.com"
 :port 4004
}
```

## Database

This project is configured to connect with a local postgres database with the same name as the project, `{{name}}`.

See the `scripts` directory for a rebuild script. Database sql files are in `resources/sql`.


## Auth

The project uses the Buddy library to support authentication. For an example, run the `scripts/test-data.sh` script.

This will create an account with the following credentials.

```
Email: foo@foo.com
Password: password
```

If you want to consider this the `Administrator` then set admin-email to foo@foo.com in the ~/.{{name}}/config.edn file.


## Selmer and Templates

See `resources/templates` for the initial templates included with the project.


## Usage


```
lein uberjar
java -jar {{name}}-standalone.jar
```


## License

Copyright © 2019 Brad Lucas

This program and the accompanying materials are made available under the
terms of the Eclipse Public License 2.0 which is available at
http://www.eclipse.org/legal/epl-2.0.

This Source Code may also be made available under the following Secondary
Licenses when the conditions for such availability set forth in the Eclipse
Public License, v. 2.0 are satisfied: GNU General Public License as published by
the Free Software Foundation, either version 2 of the License, or (at your
option) any later version, with the GNU Classpath Exception which is available
at https://www.gnu.org/software/classpath/license.html.

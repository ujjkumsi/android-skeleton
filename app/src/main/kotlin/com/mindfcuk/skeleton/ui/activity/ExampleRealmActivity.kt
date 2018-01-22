/*******************************************************************************
 * Copyright 2018 mindfcuk
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and
 * associated documentation files (the "Software"), to deal in the Software without restriction, including
 * without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to
 * the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or substantial
 * portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED,
 * INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A
 * PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR
 * COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN
 * ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH
 * THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 ******************************************************************************/

package com.mindfcuk.skeleton.ui.activity

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.LinearLayout
import android.widget.TextView
import com.mindfcuk.skeleton.R
import com.mindfcuk.skeleton.db.model.Cat
import com.mindfcuk.skeleton.db.model.Dog
import com.mindfcuk.skeleton.db.model.Person
import io.realm.Realm
import io.realm.Sort
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread
import timber.log.Timber
import kotlin.properties.Delegates

class ExampleRealmActivity : AppCompatActivity() {

    private var rootLayout: LinearLayout by Delegates.notNull()
    private var realm: Realm by Delegates.notNull()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        rootLayout = findViewById(R.id.container)
        rootLayout.removeAllViews()

        // These operations are small enough that
        // we can generally safely run them on the UI thread.

        // Open the realm for the UI thread.
        realm = Realm.getDefaultInstance()

        // Delete all persons
        // Using executeTransaction with a lambda reduces code size and makes it impossible
        // to forget to commit the transaction.
        realm.executeTransaction {
            realm.deleteAll()
        }

        basicCRUD(realm)
        basicQuery(realm)
        basicLinkQuery(realm)

        // More complex operations can be executed on another thread, for example using
        // Anko's doAsync extension method.
        doAsync {
            var info = complexReadWrite()
            info += complexQuery()
            uiThread {
                showStatus(info)
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        realm.close() // Remember to close Realm when done.
    }

    private fun showStatus(txt: String) {
        Timber.i(txt)
        val tv = TextView(this)
        tv.text = txt
        rootLayout.addView(tv)
    }

    private fun basicCRUD(realm: Realm) {
        showStatus("Perform basic Create/Read/Update/Delete (CRUD) operations...")

        // All writes must be wrapped in a transaction to facilitate safe multi threading
        realm.executeTransaction {
            // Add a person
            val person = realm.createObject(Person::class.java, 0)
            person.name = "Young Person"
            person.age = 14
        }

        // Find the first person (no query conditions) and read a field
        val person = realm.where(Person::class.java).findFirst()!!
        showStatus(person.name + ": " + person.age)

        // Update person in a transaction
        realm.executeTransaction {
            person.name = "Senior Person"
            person.age = 99
            showStatus(person.name + " got older: " + person.age)
        }
    }

    private fun basicQuery(realm: Realm) {
        showStatus("\nPerforming basic Query operation...")
        showStatus("Number of persons: ${realm.where(Person::class.java).count()}")

        val ageCriteria = 99
        val results = realm.where(Person::class.java).equalTo("age", ageCriteria).findAll()

        showStatus("Size of result set: " + results.size)
    }

    private fun basicLinkQuery(realm: Realm) {
        showStatus("\nPerforming basic Link Query operation...")
        showStatus("Number of persons: ${realm.where(Person::class.java).count()}")

        val results = realm.where(Person::class.java).equalTo("cats.name", "Tiger").findAll()

        showStatus("Size of result set: ${results.size}")
    }

    private fun complexReadWrite(): String {
        var status = "\nPerforming complex Read/Write operation..."

        // Open the default realm. All threads must use its own reference to the realm.
        // Those can not be transferred across threads.
        val realm = Realm.getDefaultInstance()
        try {
            // Add ten persons in one transaction
            realm.executeTransaction {
                val fido = realm.createObject(Dog::class.java)
                fido.name = "fido"
                for (i in 1..9) {
                    val person = realm.createObject(Person::class.java, i.toLong())
                    person.name = "Person no. $i"
                    person.age = i
                    person.dog = fido

                    // The field tempReference is annotated with @Ignore.
                    // This means setTempReference sets the Person tempReference
                    // field directly. The tempReference is NOT saved as part of
                    // the RealmObject:
                    person.tempReference = 42

                    for (j in 0..i - 1) {
                        val cat = realm.createObject(Cat::class.java)
                        cat.name = "Cat_$j"
                        person.cats.add(cat)
                    }
                }
            }

            // Implicit read transactions allow you to access your objects
            status += "\nNumber of persons: ${realm.where(Person::class.java).count()}"

            // Iterate over all objects
            for (person in realm.where(Person::class.java).findAll()) {
                val dogName: String = person?.dog?.name ?: "None"

                status += "\n${person.name}: ${person.age} : $dogName : ${person.cats.size}"

                // The field tempReference is annotated with @Ignore
                // Though we initially set its value to 42, it has
                // not been saved as part of the Person RealmObject:
                check(person.tempReference == 0)
            }

            // Sorting
            val sortedPersons = realm.where(Person::class.java).findAllSorted(Person::age.name, Sort.DESCENDING)
            status += "\nSorting ${sortedPersons.last()?.name} == ${realm.where(Person::class.java).findAll().first()?.name}"

        } finally {
            realm.close()
        }
        return status
    }

    private fun complexQuery(): String {
        var status = "\n\nPerforming complex Query operation..."

        // Realm implements the Closable interface, therefore we can make use of Kotlin's built-in
        // extension method 'use' (pun intended).
        Realm.getDefaultInstance().use {
            // 'it' is the implicit lambda parameter of type Realm
            status += "\nNumber of persons: ${it.where(Person::class.java).count()}"

            // Find all persons where age between 7 and 9 and name begins with "Person".
            val results = it
                    .where(Person::class.java)
                    .between("age", 7, 9)       // Notice implicit "and" operation
                    .beginsWith("name", "Person")
                    .findAll()

            status += "\nSize of result set: ${results.size}"

        }

        return status
    }
}

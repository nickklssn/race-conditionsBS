package com.example.race_conditions

import android.os.Bundle
import android.os.SystemClock
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import java.util.concurrent.Semaphore
import java.lang.Thread

class MainActivity : AppCompatActivity() {

    private lateinit var textView: TextView
    private var counter = 0


    // Bedingungen für die einzelnen Threads
    /*private var a = true
    private var b = true
    private var c = true*/

    private var semo = Semaphore(1,true)


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        textView = findViewById(R.id.counterView)


        // Alle 4 Thread arbeiten parallel zueinander und haben daher jeweils Lese -und Schreibezugriff auf den Counter
        // Da alle Threads dieselbe Priorität haben, entscheidet das BS, wie die Threads ablaufen


        // Methode 1: Jeder Thread setzt den State einer Variablen auf false, damit der nächste Thread laufen kann
       /* Thread {
            for (i in 1..150000) counter += 1


            for (i in 1..150000) counter -= 1
            textView.text = counter.toString()

            a = false
                                                }.start()

        if (a == false)
            Thread {
                for (i in 1..150000) counter -= 1
                textView.text = counter.toString()

                for (i in 1..150000) counter += 1

                b = false

            }.start()


        if (b == false)
        Thread {
            for (i in 1..150000) counter -= 1
            counter +=1
            textView.text = counter.toString()

            c = false
                                                }.start()

        if (c==false)
        Thread {
            for (i in 1..150000) counter += 1
            counter -= 1
            textView.text = counter.toString()
                                                }.start()
    }*/



        // 2. Methode: Benutzen von Semaphoren
        // Ein Semaphor hat eine bestimmte Anzahl an permits (Anzahl an Ausführungen)
        // Nur ein Thread kann diesen Permit beanspruchen (semo.aquire)
        // Ist der Thread fertig mit der Ausführung, gibt er diesen Permit ab (semo.release)

            Thread {

                semo.acquire()
                for (i in 1..150000) counter += 1


                for (i in 1..150000) counter -= 1
                textView.text = counter.toString()

                semo.release()
                    }.start()



            Thread {

                semo.acquire()
                for (i in 1..150000) counter -= 1
                textView.text = counter.toString()

                for (i in 1..150000) counter += 1
                semo.release()



                    }.start()



            Thread {

                semo.acquire()
                for (i in 1..150000) counter -= 1
                counter +=1
                textView.text = counter.toString()
                semo.release()


                    }.start()




            Thread {
                semo.acquire()
                for (i in 1..150000) counter += 1
                counter -= 1
                textView.text = counter.toString()
                semo.release()
                    }.start()
    }
}


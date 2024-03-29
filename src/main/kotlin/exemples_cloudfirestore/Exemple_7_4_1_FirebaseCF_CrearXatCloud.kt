package exemples_cloudfirestore

import java.awt.EventQueue
import javax.swing.JFrame
import javax.swing.JLabel
import javax.swing.JTextArea
import javax.swing.JButton
import javax.swing.JTextField
import javax.swing.JPanel
import java.awt.BorderLayout
import java.awt.FlowLayout
import java.awt.GridLayout
import java.awt.Color
import javax.swing.JScrollPane
import java.io.FileInputStream

import com.google.api.core.ApiFuture
import com.google.auth.oauth2.GoogleCredentials
import com.google.cloud.firestore.DocumentChange
import com.google.cloud.firestore.DocumentReference
import com.google.cloud.firestore.DocumentSnapshot
import com.google.cloud.firestore.EventListener
import com.google.cloud.firestore.Firestore
import com.google.cloud.firestore.FirestoreException
import com.google.firebase.FirebaseApp
import com.google.firebase.FirebaseOptions
import com.google.firebase.cloud.FirestoreClient

class CrearXat : JFrame() {

    val etUsuari = JLabel("Nom Usuari:")
    val usuari = JTextField(25)

    val etUltimMissatge = JLabel("Últim missatge: ")
    val ultimMissatge = JLabel()

    val etiqueta = JLabel("Missatges:")
    val area = JTextArea()

    val etIntroduccioMissatge = JLabel("Introdueix missatge:")
    val enviar = JButton("Enviar")
    val missatge = JTextField(15)

    // en iniciar posem un contenidor per als elements anteriors
    init {
        defaultCloseOperation = JFrame.EXIT_ON_CLOSE
        setBounds(100, 100, 450, 300)
        setLayout(BorderLayout())
        // contenidor per als elements
        //Hi haurà títol. Panell de dalt: últim missatge. Panell de baix: per a introduir missatge. Panell central: tot el xat

        val panell11 = JPanel(FlowLayout())
        panell11.add(etUsuari)
        panell11.add(usuari)
        val panell12 = JPanel(FlowLayout())
        panell12.add(etUltimMissatge)
        panell12.add(ultimMissatge)
        val panell1 = JPanel(GridLayout(2, 1))
        panell1.add(panell11)
        panell1.add(panell12)
        getContentPane().add(panell1, BorderLayout.NORTH)

        val panell2 = JPanel(BorderLayout())
        panell2.add(etiqueta, BorderLayout.NORTH)
        area.setForeground(Color.blue)
        area.setEditable(false)
        val scroll = JScrollPane(area)
        panell2.add(scroll, BorderLayout.CENTER)
        getContentPane().add(panell2, BorderLayout.CENTER)

        val panell3 = JPanel(FlowLayout())
        panell3.add(etIntroduccioMissatge)
        panell3.add(missatge)
        panell3.add(enviar)
        getContentPane().add(panell3, BorderLayout.SOUTH)

        setVisible(true)
        enviar.addActionListener { enviar() }

        val serviceAccount = FileInputStream("acces-a-dades-6e5a6-firebase-adminsdk-ei7uc-fcf7da56aa.json")

        val options = FirebaseOptions.builder()
            .setCredentials(GoogleCredentials.fromStream(serviceAccount))
            .build()

        FirebaseApp.initializeApp(options)

        // Exemple de lectura única: senzillament sobre un ApiFuture i sobre ell get()
        // Per a posar el títol. Sobre /Xats/XatProva/nomXat

        // Exemple de listener de lectura contínua addSnapshotListener()
        // Per a posar l'últim missatge registrat. Sobre /Xats/XatProva/ultimUsuari i /Xats/XatProva/ultimMissatge DocumentSnapshot FirestoreException

        // Exemple de listener de lectura contínua addSnapshotListener() sobre una col·lecció
        // Per a posar tota la llista de missatges. Sobre /Xats/XatProva/missatges

    }

    // Exemple de guardar dades en Cloud Firestore
    // Per a guardar dades. Sobre /Xats/XatProva i després sobre /Xats/Xat1
    fun enviar() {

    }
}

fun main(args: Array<String>) {
    EventQueue.invokeLater {
        CrearXat().isVisible = true
    }
}
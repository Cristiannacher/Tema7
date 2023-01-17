package exemples_cloudstorage

import javax.swing.JFrame
import java.awt.EventQueue
import javax.swing.JTextField
import javax.swing.JButton
import javax.swing.JLabel
import java.awt.BorderLayout
import javax.swing.JPanel
import java.awt.FlowLayout
import com.google.firebase.FirebaseOptions
import com.google.auth.oauth2.GoogleCredentials
import com.google.firebase.FirebaseApp
import com.google.cloud.storage.Bucket
import com.google.firebase.cloud.StorageClient
import java.nio.file.Paths
import java.awt.image.BufferedImage
import java.io.*
import javax.imageio.ImageIO
import java.nio.ByteBuffer
import javax.swing.ImageIcon

class AgafarImatge_1 : JFrame() {
    val nomIm = JTextField(25)
    val boto = JButton("Agafar")

    val foto = JLabel()
    var bucket: Bucket? = null

    init {
        defaultCloseOperation = JFrame.EXIT_ON_CLOSE
        setBounds(100, 100, 900, 600)
        setLayout(BorderLayout())

        val panell1 = JPanel(FlowLayout())
        panell1.add(nomIm)
        panell1.add(boto)
        getContentPane().add(panell1, BorderLayout.NORTH)

        getContentPane().add(foto, BorderLayout.CENTER)

        boto.addActionListener { agafar() }

        val serviceAccount = FileInputStream("xat-ad-9f901-firebase-adminsdk-f1vja-30104618f8.json")
        val options = FirebaseOptions.Builder()
            .setCredentials(GoogleCredentials.fromStream(serviceAccount))
            .build()

        FirebaseApp.initializeApp(options)
        bucket = StorageClient.getInstance().bucket("xat-ad-9f901.appspot.com")
    }


    fun agafar() {
        // Instruccions per agafar la imatge
        val blob = bucket?.get(nomIm.getText())
        val f = File("auxiliar")
        blob?.downloadTo(FileOutputStream(f))
        val image = ImageIO.read(f)
        foto.setIcon(ImageIcon(image))

    }

}

fun main(args: Array<String>) {
    EventQueue.invokeLater {
        AgafarImatge_1().isVisible = true
    }
}
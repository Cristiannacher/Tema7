import com.google.auth.oauth2.GoogleCredentials
import com.google.firebase.FirebaseApp
import com.google.firebase.FirebaseOptions
import java.io.FileInputStream
import javax.swing.JFrame
import javax.swing.JLabel
import javax.swing.JComboBox
import javax.swing.JTextArea
import java.awt.BorderLayout
import javax.swing.JPanel
import java.awt.FlowLayout
import java.awt.Color
import javax.swing.JScrollPane
import com.google.firebase.database.*
import java.awt.EventQueue

fun main() {

    EventQueue.invokeLater {
        EstadisticaRD().isVisible = true
    }
}

class EstadisticaRD : JFrame() {

    val etProv = JLabel("Provincia: ")
    val provincia = JComboBox<String>()

    val etiqueta = JLabel("Missatges:")
    val area = JTextArea()

    init {
        defaultCloseOperation = JFrame.EXIT_ON_CLOSE
        setBounds(100, 100, 450, 450)
        setLayout(BorderLayout())

        val panell1 = JPanel(FlowLayout())
        panell1.add(etProv)
        panell1.add(provincia)
        getContentPane().add(panell1, BorderLayout.NORTH)

        val panell2 = JPanel(BorderLayout())
        panell2.add(etiqueta, BorderLayout.NORTH)
        area.setForeground(Color.blue)
        area.setEditable(false)
        val scroll = JScrollPane(area)
        panell2.add(scroll, BorderLayout.CENTER)
        getContentPane().add(panell2, BorderLayout.CENTER)

        setVisible(true)

        val serviceAccount = FileInputStream("xat-ad-9f901-firebase-adminsdk-f1vja-30104618f8.json")

        val options = FirebaseOptions.Builder()
            .setCredentials(GoogleCredentials.fromStream(serviceAccount))
            .setDatabaseUrl("https://xat-ad-9f901-default-rtdb.europe-west1.firebasedatabase.app/").build()

        FirebaseApp.initializeApp(options)
        val database = FirebaseDatabase.getInstance()

        // Posar tota la llista de províncies al JComboBox anomenat provincia
        val ref = database.getReference("EstadisticaVariacioPoblacional")
        ref.addChildEventListener(object : ChildEventListener {
            override fun onChildAdded(p0: DataSnapshot, p1: String?) {
                provincia.addItem(p0.child("Nombre").getValue(String::class.java) ?: "valor vacio")
            }

            override fun onChildChanged(p0: DataSnapshot?, p1: String?) {
                TODO("Not yet implemented")
            }

            override fun onChildRemoved(p0: DataSnapshot?) {
                TODO("Not yet implemented")
            }

            override fun onChildMoved(p0: DataSnapshot?, p1: String?) {
                TODO("Not yet implemented")
            }

            override fun onCancelled(p0: DatabaseError?) {
                TODO("Not yet implemented")
            }


        })
        provincia.addActionListener() {
            // Posar la informació de tots els anys en el JTextArea anomenat area
            val dataref = database.getReference("EstadisticaVariacioPoblacional/${provincia.selectedIndex}/Data")
            dataref.addChildEventListener(object : ChildEventListener {
                override fun onChildAdded(p0: DataSnapshot, p1: String?) {
                    val nombrePeriodo = p0.child("NombrePeriodo").getValue(String::class.java) ?: "no se ha encontrado elnombre"
                    val valor = p0.child("Valor").getValue(Int::class.java) ?: "Vacio"
                    area.append("Año: $nombrePeriodo -- Variacion: $valor \n")
                }

                override fun onChildChanged(p0: DataSnapshot?, p1: String?) {
                    TODO("Not yet implemented")
                }

                override fun onChildRemoved(p0: DataSnapshot?) {
                    TODO("Not yet implemented")
                }

                override fun onChildMoved(p0: DataSnapshot?, p1: String?) {
                    TODO("Not yet implemented")
                }

                override fun onCancelled(p0: DatabaseError?) {
                    TODO("Not yet implemented")
                }

            })
        }
    }
}


import android.database.Cursor
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    private var eventosDataSource: EventosDataSource? = null
    private var invitadosDataSource: InvitadosDataSource? = null
    private var usuariosDataSource: UsuariosDataSource? = null
    protected fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        eventosDataSource = EventosDataSource(this)
        eventosDataSource.open()
        invitadosDataSource = InvitadosDataSource(this)
        invitadosDataSource.open()
        usuariosDataSource = UsuariosDataSource(this)
        usuariosDataSource.open()

        // Insertar un nuevo evento
        val nuevoEvento = Evento()
        nuevoEvento.setNombre("Evento de Prueba")
        nuevoEvento.setLugar("Lugar de Prueba")
        // ... Configura los demás atributos
        val nuevoEventoId: Long = eventosDataSource.insertEvento(nuevoEvento)
        if (nuevoEventoId != -1L) {
            Toast.makeText(this, "Evento insertado correctamente", Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(this, "Error al insertar evento", Toast.LENGTH_SHORT).show()
        }

        // Obtener todos los eventos
        val eventosCursor: Cursor = eventosDataSource.getAllEventos()
        if (eventosCursor != null && eventosCursor.moveToFirst()) {
            do {
                val id: Int = eventosCursor.getInt(eventosCursor.getColumnIndex(EventoContract.EventoEntry._ID))
                val nombre: String = eventosCursor.getString(eventosCursor.getColumnIndex(EventoContract.EventoEntry.COLUMN_NOMBRE))
                // ... Obtén los demás atributos

                // Realiza acciones con los datos recuperados
                // Por ejemplo, mostrarlos en un ListView o RecyclerView
            } while (eventosCursor.moveToNext())
            eventosCursor.close()
        }

        // Actualizar un evento
        val eventoIdAActualizar: Long = 1 // ID del evento a actualizar
        val eventoActualizado = Evento()
        eventoActualizado.setNombre("Nuevo Nombre de Evento")
        // ... Configura los demás atributos
        val filasActualizadas: Int = eventosDataSource.updateEvento(eventoIdAActualizar, eventoActualizado)
        if (filasActualizadas > 0) {
            Toast.makeText(this, "Evento actualizado correctamente", Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(this, "Error al actualizar evento", Toast.LENGTH_SHORT).show()
        }

        // Eliminar un evento
        val eventoIdAEliminar: Long = 2 // ID del evento a eliminar
        val filasEliminadas: Int = eventosDataSource.deleteEvento(eventoIdAEliminar)
        if (filasEliminadas > 0) {
            Toast.makeText(this, "Evento eliminado correctamente", Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(this, "Error al eliminar evento", Toast.LENGTH_SHORT).show()
        }

        // Crear y registrar un invitado
        val invitado = Usuario()
        invitado.setNombres("Juan")
        invitado.setApellidoPaterno("Pérez")
        // ... Configura el resto de los atributos
        val invitadoId: Long = invitadosDataSource.insertInvitado(invitado)
        if (invitadoId != -1L) {
            // Invitado registrado exitosamente
        } else {
            // Error al registrar el invitado
        }

        // Crear y registrar un usuario
        val usuario = Usuario()
        usuario.setNombres("María")
        usuario.setApellidoPaterno("García")
        // ... Configura el resto de los atributos
        val usuarioId: Long = usuariosDataSource.insertUsuario(usuario)
        if (usuarioId != -1L) {
            // Usuario registrado exitosamente
        } else {
            // Error al registrar el usuario
        }
    }

    protected fun onDestroy() {
        super.onDestroy()
        eventosDataSource.close()
        invitadosDataSource.close()
        usuariosDataSource.close()
    }
}
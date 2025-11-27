package edu.eam.ingesoft.fundamentos.parqueadero.logica;

import java.util.ArrayList;

/**
 * Clase principal que gestiona todas las operaciones del parqueadero.
 * Coordina propietarios, vehículos y servicios.
 */
public class Parqueadero {

    // ==================== ATRIBUTOS ====================

    private ArrayList<Propietario> propietarios;
    private ArrayList<Vehiculo> vehiculos;
    private ArrayList<Servicio> servicios;

    // ==================== CONSTRUCTOR ====================

    /**
     * Crea una nueva instancia del Parqueadero con las listas vacías.
     */
    public Parqueadero() {
        this.propietarios = new ArrayList<>();
        this.vehiculos = new ArrayList<>();
        this.servicios = new ArrayList<>();
    }

    // ==================== MÉTODOS DE BÚSQUEDA ====================

    /**
     * Busca un propietario en el sistema por su cédula.
     * Debe recorrer la lista de propietarios usando foreach.
     * @param cedula Cédula del propietario a buscar
     * @return El propietario encontrado, o null si no existe
     */
    public Propietario buscarPropietario(String cedula) {
        for (Propietario propietario : propietarios) {
            if (propietario.getCedula().equals(cedula)) {
                return propietario;
            }
    }

        return null;
    }

    /**
     * Busca un vehículo en el sistema por su placa.
     * Debe recorrer la lista de vehículos usando foreach.
     * @param placa Placa del vehículo a buscar
     * @return El vehículo encontrado, o null si no existe
     */
    public Vehiculo buscarVehiculo(String placa) {
            for (Vehiculo vehiculo : vehiculos) {
            if (vehiculo.getPlaca().equals(placa)) {
                return vehiculo;
            }
    }

        return null;
    }

    // ==================== MÉTODOS DE REGISTRO ====================

    /**
     * Registra un nuevo propietario en el sistema.
     * Debe validar que la cédula no exista antes de registrar.
     * @param cedula Cédula del nuevo propietario
     * @param nombre Nombre del nuevo propietario
     * @return true si se registró exitosamente, false si la cédula ya existe
     */
    public boolean registrarPropietario(String cedula, String nombre) {
        if (buscarPropietario(cedula) == null) {
            Propietario nuevoPropietario = new Propietario(cedula, nombre);
            propietarios.add(nuevoPropietario);
            return true;
        }
        return false;
    }

    /**
     * Registra un nuevo vehículo en el sistema.
     * Debe validar que:
     * 1. La placa no exista
     * 2. El propietario exista
     * @param placa Placa del nuevo vehículo
     * @param modelo Año del vehículo
     * @param color Color del vehículo
     * @param cedula Cédula del propietario del vehículo
     * @param tipo Tipo de vehículo ("SEDAN", "SUV" o "CAMION")
     * @return true si se registró exitosamente, false si la placa ya existe o el propietario no existe
     */
    public boolean registrarVehiculo(String placa, int modelo, String color, String cedula, String tipo) {
        Propietario propietario = buscarPropietario(cedula);
        if (buscarVehiculo(placa) == null && propietario != null) {
            Vehiculo nuevoVehiculo = new Vehiculo(placa, modelo, color, propietario, tipo);
            vehiculos.add(nuevoVehiculo);
            return true;
        }
        return false;
    }

    // ==================== MÉTODO PARA ACUMULAR HORAS ====================

    /**
     * Acumula horas de uso a un cliente específico.
     * Debe buscar el propietario y delegar la acumulación de horas.
     * @param cedula Cédula del propietario
     * @param horas Número de horas a acumular
     * @return true si se acumularon las horas, false si el propietario no existe
     */
    public boolean acumularHorasCliente(String cedula, int horas) {
        Propietario propietario = buscarPropietario(cedula);
        if (propietario != null) {
            propietario.acumularHoras(horas);
            return true;
        }
        return false;
    }

    // ==================== MÉTODO DE REGISTRO DE SERVICIO ====================

    /**
     * Registra un nuevo servicio de parqueo con todas las validaciones.
     * Debe validar:
     * 1. Hora de ingreso válida (1-22)
     * 2. Hora de salida válida (2-23)
     * 3. Hora de salida mayor que hora de ingreso
     * 4. El vehículo existe
     *
     * Si todo es válido:
     * - Crear el servicio
     * - Acumular las horas al propietario del vehículo
     * - Agregar el servicio a la lista
     *
     * @param placa Placa del vehículo que usará el servicio
     * @param horaIngreso Hora de entrada (1-22)
     * @param horaSalida Hora de salida (2-23)
     * @return El costo del servicio, o -1 si falla alguna validación
     */
    public double registrarServicio(String placa, int horaIngreso, int horaSalida) {
        if (horaIngreso < 1 || horaIngreso > 22) {
            return -1;
        }
        if (horaSalida < 2 || horaSalida > 23) {
            return -1;
        }
        if (horaSalida <= horaIngreso) {
            return -1;
        }
        Vehiculo vehiculo = buscarVehiculo(placa);
        if (vehiculo != null) {
            Servicio nuevoServicio = new Servicio(horaIngreso, horaSalida, vehiculo);
            servicios.add(nuevoServicio);
            int horasServicio = nuevoServicio.calcularHoras();
            vehiculo.getPropietario().acumularHoras(horasServicio);
            return nuevoServicio.getCosto();
        }
        return -1;
    }

    // ==================== MÉTODOS DE ESTADÍSTICAS ====================

    /**
     * Calcula el total de dinero recaudado por todos los servicios.
     * Debe recorrer la lista de servicios y sumar los costos.
     * @return La suma total de todos los costos de servicios
     */
    public double calcularTotalRecaudado() {
        double total = 0;
        for (Servicio servicio : servicios) {
            total += servicio.getCosto();
        }
        return total;
    }

    /**
     * Cuenta cuántos clientes tienen categoría VIP.
     * Debe recorrer la lista de propietarios y contar los VIP.
     * @return Cantidad de clientes VIP
     */
    public int contarClientesVIP() {
        int contador = 0;
        for (Propietario propietario : propietarios) {
            if (propietario.esVIP()) {
                contador++;
            }
        }
        return contador;
    }

    /**
     * Encuentra el cliente con mayor cantidad de horas acumuladas.
     * Debe recorrer la lista de propietarios buscando el máximo.
     * @return El propietario con más horas, o null si no hay propietarios
     */
    public Propietario obtenerClienteMasHoras() {
        Propietario clienteMasHoras = null;
        int maxHoras = -1;
        for (Propietario propietario : propietarios) {
            if (propietario.getHorasAcumuladas() > maxHoras) {
                maxHoras = propietario.getHorasAcumuladas();
                clienteMasHoras = propietario;
            }
        }
        return clienteMasHoras;
    }

    // ==================== GETTERS PARA LAS LISTAS ====================

    /**
     * @return La lista de propietarios registrados
     */
    public ArrayList<Propietario> getPropietarios() {
        return propietarios;
    }

    /**
     * @return La lista de vehículos registrados
     */
    public ArrayList<Vehiculo> getVehiculos() {
        return vehiculos;
    }

    /**
     * @return La lista de servicios registrados
     */
    public ArrayList<Servicio> getServicios() {
        return servicios;
    }
}

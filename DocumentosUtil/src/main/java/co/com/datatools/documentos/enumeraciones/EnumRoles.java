package co.com.datatools.documentos.enumeraciones;

public enum EnumRoles {
    ADMINISTRADOR(1), //
    EDITOR(2), //
    ;
    private int idRol;

    private EnumRoles(int idRol) {
        this.idRol = idRol;
    }

    public int getIdRol() {
        return idRol;
    }

}

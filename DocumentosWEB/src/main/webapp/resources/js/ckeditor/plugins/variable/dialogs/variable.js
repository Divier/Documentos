CKEDITOR.dialog.add( 'variable', function( editor ) {
    return {
        title: 'Adicionar Variable',
        minWidth: 200,
        minHeight: 100,
        contents: [
            {
                id: 'info',
                elements: [
                    {
                        id: 'nombreVariable',
                        type: 'select',
                        label: 'Variable',
                        items: Documentos.Plantillas.variablesArray,
                        width: "100%",
                        setup: function( widget ) {
                        	this.setValue( widget.data.idVariable );
                            var $input = $(this.getInputElement().$);
                            $input.empty();
                            //Se colocan los grupos en el drop down para los ordenes jerarquicos de las variables
                            $.each(Documentos.Plantillas.ordenesJerarquicos, function (index) {
                               var optgroup = $('<optgroup>');
                               optgroup.attr('label',Documentos.Plantillas.ordenesJerarquicos[index].nombre);

                                $.each(Documentos.Plantillas.ordenesJerarquicos[index].opciones, function (i) {
                                   var option = $("<option></option>");
                                   option.val(Documentos.Plantillas.ordenesJerarquicos[index].opciones[i][1]);
                                   option.text(Documentos.Plantillas.ordenesJerarquicos[index].opciones[i][0]);

                                   optgroup.append(option);
                                });
                                $input.append(optgroup);

                            });
                        },
                        commit: function( widget ) {
                            widget.setData( 'idVariable', this.getValue());
	            	        Documentos.Plantillas.isEditVariable = false; 
                        }
                    }
                ]
            }
        ]
    };
} );
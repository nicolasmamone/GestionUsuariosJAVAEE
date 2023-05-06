const BASE_URL = 'http://localhost:8080/gestionUsuarios';


let btnNewModal = document.getElementById('btn-nuevo');
btnNewModal.addEventListener('click', showNewModal); 
let btnGuardar = document.getElementById('btn-guardar');
btnGuardar.addEventListener('click', guardarUsuario);
let txtSearch = document.getElementById('txt-search');
txtSearch.addEventListener('keyup', buscarUsuarios);

function buscarUsuarios(){
    let url = BASE_URL + '/usuarios';
    let terminoBuscado = txtSearch.value;
    console.log(terminoBuscado);
    let params = `?search=${terminoBuscado}`;

    fetch(url + params).then(
        response => response.json()
    ).then(
        jsonSearchResult => {
            generarTabla(jsonSearchResult.data);
        }
    )
}

//limpia los campos del modal
function showNewModal(event){
    $('#modal-titulo-usuario').html('Nuevo Usuario')
    let inputs = document.querySelectorAll('#modal-editor-usuario input'); 
    for (let i = 0; i < inputs.length; i++) {
        inputs[i].value = '';
    }
    $('#modal-editor-usuario').modal('show');

}

//cargar los campos con los datos y cambiar el titulo
function showEditModal(event){
    let idUsuario = event.currentTarget.getAttribute('data-usuario-id'); 
    getUsuario(idUsuario).then(
        json => {
            let dataUsuario = json.data[0];

            $('#id-usuario').val(dataUsuario.id);
            $('#nombre').val(dataUsuario.nombre);
            $('#apellido').val(dataUsuario.apellido);

            $('#modal-titulo-usuario').html('Editar Cliente')
            $('#modal-editor-usuario').modal('show');
        }
    )

}
function eliminarUsuario(event){
    let idUsuario = event.currentTarget.getAttribute('data-usuario-id'); 

    Swal.fire({ //el fire devuelve una promesa
        title: 'Confirma que desea eliminar el usuario seleccionado?' ,
        icon: 'question' ,
        showCancelButton: true,
        confirmButtonText: 'Eliminar' ,
        cancelButtonText: 'Cancelar', 
    }).then( //la promesa termina cuando cliqueo eliminar o cancelar
        result => {
            if(result.value){ //si hay algo en el result, es que cliqueo aceptar..
                let url = BASE_URL + '/usuarios?id=' + idUsuario; //endpoint
                fetch(url, {method: 'DELETE' })
                .then(
                    response => response.json()
                ).then(
                    data =>{ 
                        Swal.fire({
                            title: 'Eliminado' ,
                            text: data.message ,
                            icon: 'success' ,
                            confirmButtonText: 'Aceptar'
                        });
                        cargarListadoUsuarios();
                        }
                    )
                }
            }
        )      

    
}

function cargarListadoUsuarios(){
    txtSearch.value ='';
    getUsuarios().then(
        json => {
            generarTabla(json.data);
        }
    )
}

async function getUsuarios(){
    let url = BASE_URL + '/usuarios'; //endpoint

    let response = await fetch(url);
    let json = await response.json();

    console.log(json);
    return json;
}
async function getUsuario(id){
    let url = BASE_URL + '/usuarios?id=' + id; //endpoint

    let response = await fetch(url);
    let json = await response.json();

    return json;
}


function generarTabla(data){

    console.log(data);
    let container = document.getElementById('contenedor');  

    let contenidoHTML = '';

    for( usuario of data){
        console.log(usuario.id);
        contenidoHTML +=`
                            <tr id="fila-${usuario.id}"> 
                                <td class="pl-5">${usuario.id}</td>
                                <td>${usuario.nombre}</td>
                                <td>${usuario.apellido}</td>

                                <td>
                                    <button data-usuario-id="${usuario.id}" class="btn-editar btn btn-success">
                                        <i class="fa fa-edit"></i>
                                    </button>
									<button data-usuario-id="${usuario.id}" class="btn-eliminar btn btn-danger">
                                        <i class="fa fa-trash"></i>
                                    </button>
                                </td>
                            </tr>
                        `;
    }
    container.innerHTML = contenidoHTML;

    $('.btn-editar').click(showEditModal);
    $('.btn-eliminar').click(eliminarUsuario);
}


//guardar o modificar
function guardarUsuario(){
    let url = BASE_URL + '/usuarios';

    let nombreUsuario = $('#nombre').val(); //con jquery
    let apellidoUsuario = document.getElementById('apellido').value; // con javascript
    let params = `?nombre=${nombreUsuario}&apellido=${apellidoUsuario}`;
    let method = 'POST';

    if( $('#id-usuario').val() ){
        method = 'PUT'
        params += '&id=' + $('#id-usuario').val();
    }

    fetch(url + params,{
        method: method
    }).then(
        response => response.json() 
    ).then(
        data => {
            Swal.fire({
                title: 'Guardado' ,
                text: data.message ,
                icon: 'success' ,
                confirmButtonText: 'Aceptar' 
            })

            cargarListadoUsuarios();
            $('#modal-editor-usuario').modal('hide');
        }
    )
}








cargarListadoUsuarios();



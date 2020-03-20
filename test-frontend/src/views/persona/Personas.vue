<template>
  <div class="animated fadeIn">
   <b-card header="Filtros" header-tag="header" footer-tag="footer">
      <div>
        <b-form>
          <div>
            <b-card bg-variant="light">
              <b-row>
                <b-col class="pad-x">
                  <b-form-group>
                    <b-row>Nombre:</b-row>
                    <b-row>
                      <b-form-input id="nombre" v-model="nombreFiltro" ></b-form-input>
                    </b-row>
                  </b-form-group>
                </b-col>
               <b-col class="pad-x">
                    <b-form-group>
                      <b-row>Tipo de Documento:</b-row>
                      <b-row>
                        <b-form-select id="comboTipoDocumento" v-model="tipoDocumento" 
                                       :options="tipoDocumentoOptions">
                          <template slot="first">
                            <option :value="null" >-- Seleccionar una opcion --</option>
                          </template>
                        </b-form-select>
                      </b-row>
                    </b-form-group>
                  </b-col>
              </b-row>

            </b-card>
          </div>
        </b-form>
      </div>
      <hr/>
      <div class="button">
        <b-button class="m-1" variant="blue" @click="buscarPersona(items)">
          <v-icon name="search" ></v-icon>
          Buscar
        </b-button>
      </div>
    </b-card>
    <b-card header="Personas" header-tag="header" footer-tag="footer">
      <router-link to="/alta-persona">
        <b-button  variant="blue"><v-icon name="plus"/> Nueva Persona</b-button>
      </router-link>
      <br />
      <br />
      <div v-if="items != null">
        <b-table :small="true" responsive striped hover :items="items" :fields="fields">
          <template slot="actions" slot-scope="row">
            <b-button size="sm" class="mr-2" variant="outline-primary" @click="editPersona(row.item)"><v-icon name="edit"/></b-button>
             <b-button id="show-btn" size="sm" @click="openModal(row.index, row.item)" class="mr-2" ><v-icon name="trash-alt"/></b-button>
          </template>
        </b-table>
      </div>
      <span v-else>Cargando...</span>
    </b-card>
  </div>
</template>

<script>
import axios from "axios";
export default {
  name: "personas",
  mounted() {
    this.getPersonas();
    this.getTiposDocumento();
  },
  data() {
    return {
      items: null,
      nombreFiltro: null,
      tipoDocumento: null,
      filterItems: [],
      tipoDocumentoOptions: {},
      fields: {
        perId: {
          label: "Id",
          sortable: true
        },
        perNombre: {
          label: "Nombre",
          sortable: true
        },
        perApellido: {
          label: "Apellido",
          sortable: true
        },
        perNumeroDocumento: {
          label: "Número de Documento",
          sortable: false
        },
        perTipoDocumento: {
          label: "Tipo de Documento",
          sortable: false
        },
				fechaNacimientoFormat: {
          label: "Fecha de Nacimiento",
          sortable: false
        },
        actions: {
          label: "",
          sortable: false
        }
      }
    };
  },
  methods: {
  getTiposDocumento() {
      axios.get('http://localhost:8080/documentos').then(respuesta => {
        this.tipoDocumentoOptions = respuesta.data.data;
        this.tipoDocumentoOptions.forEach(element => {
          element.value = element.tpoDescripcion;
          element.text = element.tpoDescripcion;
        });
      });
    },
    getPersonas() {
      axios.get("http://localhost:8080/personas").then(respuesta => {
        this.items = respuesta.data.data;
        this.items.forEach(element => {
          element.perTipoDocumento = element.perTipoDocumento.tpoDescripcion;
        });
      });
    },
    editPersona(item){
    	this.$router.push({path: 'editar-persona/' + item.perId});
    },
		deletePersona(item, index){
			axios.delete("http://localhost:8080/personas/" + item.perId).then(respuesta => {
				console.log(`respuesta.data.message: ` + respuesta.data.message);
        this.getPersonas();
			});
      this.$bvModal.hide('modal-delete')
		},
    buscarPersona(items){
      
      if(this.nombreFiltro || this.tipoDocumento) {
        if(this.nombreFiltro){
        this.filterItems = this.items.filter(item => item.perNombre.toLowerCase().includes(this.nombreFiltro.toLowerCase()));
        }
        if(this.tipoDocumento){
        this.filterItems = this.items.filter(item => item.perTipoDocumento.includes(this.tipoDocumento));
        }
        if(this.nombreFiltro && this.tipoDocumento){
        this.filterItems = this.items.filter(i => i.perNombre.toLowerCase().includes(this.nombreFiltro.toLowerCase()) && i.perTipoDocumento == this.tipoDocumento);
        }
      }else {
        this.getPersonas();
        this.filterItems = this.items;
      }
      this.items = this.filterItems;
    },
    openModal(index,item, ) {
        this.$bvModal.msgBoxConfirm('¿Está seguro que desea eliminar esta persona?', {
          okTitle: 'Eliminar',
          cancelTitle: 'Volver',
           centered: true
        })
          .then(value => {
            if (value) {
              this.deletePersona(item, index)
            }
          })
          .catch(err => {
            console.log(err)
          })
      }
  }
};
</script>

<style lang="scss">
 

  .button{
    text-align-last: end;
  }

  
</style>

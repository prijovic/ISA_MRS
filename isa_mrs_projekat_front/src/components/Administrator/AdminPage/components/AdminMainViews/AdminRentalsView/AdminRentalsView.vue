<template>
  <div class="row">
    <div class="col-1"></div>
    <div class="col-10 pt-5 mb-5">
      <div class="container-fluid pe-0 ps-0 me-0 ms-0">
        <div class="align-items-center">
          <div class="row main-col">
            <div class="col d-flex justify-content-start ps-0 ms-0 me-0 pe-0">
              <div class="col-sm-10 col-md-5 col-lg-3 col-xl-3 me-1 mb-1">
                <select class="form-control" v-model="filterType" id="userType">
                  <option value="VacationRental">Rental House</option>
                  <option value="Boat">Boat</option>
                  <option value="Adventure">Adventure</option>
                </select>
              </div>
              <div class="col-1 pe-0">
                <button @click="filterButtonClicked" class="btn rounded" :class="filterActive?'btn-red':''" :disabled="filterType===null"><font-awesome-icon :icon="filterActive?'x':'filter'"></font-awesome-icon></button>
              </div>
<!-- Save changes button -->
<!--              <button v-if="hasChanged" class="btn btn-red my-auto mb-1 me-1" data-bs-toggle="modal" data-bs-target="#confirmationDialog">-->
<!--                Save Changes-->
<!--              </button>-->
<!--  Modal for submitting changes -->
<!--              <div class="modal fade" id="confirmationDialog" tabindex="-1" aria-labelledby="exampleModalLabel" aria-hidden="true">-->
<!--                <div class="modal-dialog">-->
<!--                  <div class="modal-content">-->
<!--                    <div class="modal-header">-->
<!--                      <h5 class="modal-title" id="exampleModalLabel">Users' Status Change</h5>-->
<!--                      <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>-->
<!--                    </div>-->
<!--                    <div class="modal-body">-->
<!--                      {{modalMessage}}-->
<!--                    </div>-->
<!--                    <div class="modal-footer">-->
<!--                      <button type="button" class="btn btn-red" style="margin-right: 2vh;" data-bs-dismiss="modal">No</button>-->
<!--                      <button type="button" class="btn" @click="saveChanges" data-bs-dismiss="modal">Yes</button>-->
<!--                    </div>-->
<!--                  </div>-->
<!--                </div>-->
<!--              </div>-->
            </div>
          </div>
          <div class="row main-col text-center header rounded mb-3">
            <table>
              <thead>
              <tr>
                <th></th>
                <th>Name</th>
                <th>Owner</th>
                <th>Rental Object Type</th>
                <th></th>
                <th>Active</th>
              </tr>
              </thead>
              <tbody>
              <tr class="p-1" v-for="(rentalObject, index) in this.rentalObjects" :key="index" :class="index%2!==0?'odd':'even'">
                <td class="col-1">
                  <img v-if="rentalObject.photos.length !== 0" :src="imageUrls[index]" class="img-fluid rounded border-1" alt="">
                  <font-awesome-icon v-else icon="user" class="img-fluid rounded border-1" style="height: 3vh"></font-awesome-icon>
                </td>
                <td>
                  <router-link class="profile-link" to="#">{{rentalObject.name}}</router-link>
                </td>
                <td>
                  <router-link class="profile-link" :to="'/admin/user/' + rentalObject.rentalObjectOwner.id">{{rentalObject.rentalObjectOwner.name + " " + rentalObject.rentalObjectOwner.surname}}</router-link>
                </td>
                <td>
                  {{this.rentalObjectType(rentalObject)}}
                </td>
                <td>
                  <button :id="index" class="eye-btn border-0 text-decoration-none" type="button" data-bs-toggle="modal" :data-bs-target="'#profileModal' + index">
                    <font-awesome-icon class="text-decoration-underline" icon="eye"></font-awesome-icon>
                  </button>
                  <div class="modal fade" :id="'profileModal' + index" tabindex="-1" aria-labelledby="exampleModalLabel" aria-hidden="true">
                    <div class="modal-dialog modal-dialog-centered modal-dialog-scrollable">
                      <div class="modal-content">
                        <div class="modal-header">
                          <h5 class="modal-title">{{rentalObjectType(rentalObject)}} Profile</h5>
                          <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                        </div>
                        <div class="modal-body">
                          <div class="row justify-content-center">
                            <div class="row">
                              <div class="col me-lg-3 me-md-2">
                                <div class="row text-center">
                                  <img v-if="rentalObject.photos.length !== 0" :src="imageUrls[index]" class="img-fluid rounded p-0" alt="">
                                  <font-awesome-icon v-else icon="user" class="img-fluid rounded p-0" style="background-color: #B0B8B4FF; color: white"></font-awesome-icon>
                                </div>
                              </div>
                              <div class="col">
                                <div class="row text-center">
                                  <div class="container-fluid rounded" style="border: 1px solid #008970; color: #008970" >
                                    <h5>{{rentalObject.name}}</h5>
                                    <hr>
                                    <div class="row pb-5">
                                      <div class="col text-start">
                                        <h6>Price: <span style="color: black">{{rentalObject.price}}$</span></h6>
                                        <h6>Description: <span style="color: black">{{rentalObject.description}}</span></h6>
                                        <h6>Address: <span style="color: black">{{rentalObject.address.street + " " + rentalObject.address.number + ", " + rentalObject.address.city + ", " + rentalObject.address.country}}</span></h6>
                                        <h6>Owner: <span style="color: black">{{rentalObject.rentalObjectOwner.name + " " + rentalObject.rentalObjectOwner.surname + "(" + rentalObject.rentalObjectOwner.phone + ")"}}</span></h6>
                                        <h6>Activity:
                                          <input v-if="rentalObject.isActive" class="form-check-input" type="checkbox" checked disabled>
                                          <input v-else class="form-check-input" type="checkbox" disabled></h6>
                                      </div>
                                    </div>
                                  </div>
                                </div>
                              </div>
                            </div>
                          </div>
                        </div>
                      </div>
                    </div>
                  </div>
                </td>
                <td>
                  <input class="form-check-input" type="checkbox" :value="!rentalObject.isActive" v-model="rentalObject.isActive">
                </td>
              </tr>
              </tbody>
            </table>
            <nav aria-label="Page navigation">
              <ul class="pagination justify-content-center mt-3">
                <li class="page-item" v-if="totalPages > 1"><button class="page-link" :disabled="currentPage===0" @click="previousPage">Previous</button></li>
                <li class="page-item mt-auto me-1 ms-1" v-if="currentPage > 1 && totalPages > 3">...</li>
                <li class="page-item"><button class="page-link" :disabled="currentPage === 0" @click="numberedPage(1)">{{button1Content}}</button></li>
                <li class="page-item" v-if="totalPages > 1"><button class="page-link" :disabled="currentPage !== 0 && totalPages - currentPage > 1" @click="numberedPage(2)">{{button2Content}}</button></li>
                <li class="page-item" v-if="totalPages > 2"><button class="page-link" :disabled="totalPages - currentPage === 1" @click="numberedPage(3)">{{button3Content}}</button></li>
                <li class="page-item mt-auto me-1 ms-1" v-if="totalPages - currentPage > 1 && totalPages > 3 && button3Content !== totalPages">...</li>
                <li class="page-item" v-if="totalPages > 1"><button class="page-link" :disabled="totalPages - currentPage === 1" @click="nextPage">Next</button></li>
              </ul>
            </nav>
          </div>
        </div>
      </div>
      <div class="col-1"></div>
    </div>
  </div>
</template>

<script>
import {FontAwesomeIcon} from "@fortawesome/vue-fontawesome";
import { library } from "@fortawesome/fontawesome-svg-core";
import {faEye, faTrash, faFilter, faX} from "@fortawesome/free-solid-svg-icons";
import axios from "axios";
import {useStore} from "vuex";

library.add(faEye, faTrash, faFilter, faX);

export default {
  name: "AdminRentalsView",
  components: {FontAwesomeIcon},
  data() {
    return {
      rentalObjects: [],
      currentPage: 0,
      totalPages: null,
      pageSize: 10,
      filterType: null,
      filterActive: false,
      imageUrls: []
    }
  },
  mounted() {
    const store = useStore();
    axios.get("/RentalObjects/getRentalObjectsPage", {headers: {
        Authorization: "Bearer " + store.getters.access_token,
      },
      params: {
        page: this.currentPage,
        pageSize: this.pageSize
      }
    })
        .then(response => {
          this.rentalObjects = response.data.content;
          this.currentPage = response.data.currentPage;
          this.totalPages = response.data.pages;
          this.rentalObjects.forEach(rentalObject => {
            if (rentalObject.photos.length > 0) {
              const index = this.rentalObjects.indexOf(rentalObject);
              this.loadImage(rentalObject.photos[0].photo, index);
            }
          })
        })
        .catch(() =>{
          this.$notify({
            title: "Server error",
            text: "Server is currently off. Please try again later...",
            type: "error"
          });
        });
  },
  methods: {
    loadImage(name, index) {
      axios.get("/Photos/", {headers: {
          Authorization: "Bearer " + this.$store.getters.access_token,
        },
        params: {
          path: name,
        },
        responseType: "blob"
      })
          .then(response => {
            this.imageUrls[index] = URL.createObjectURL(response.data);
          })
          .catch((error) =>{
            console.log(error);
          });
    },
    filterButtonClicked() {
      if (this.filterActive) {
        this.filterType = null;
        this.filterActive = !this.filterActive;
        this.refreshPage();
      } else if (this.filterType !== null) {
        this.filterActive = !this.filterActive;
        this.refreshPage();
      }
    },
    rentalObjectType(rentalObject) {
      if (rentalObject.rentalObjectType === "Adventure") {
        return "Adventure";
      } else if (rentalObject.rentalObjectType === "VacationRental") {
        return "Rental House";
      } else {
        return rentalObject.rentalObjectType;
      }
    },
    previousPage() {
      this.currentPage -= 1;
      this.refreshPage();
    },
    numberedPage(buttonNumber) {
      if (buttonNumber === 1) {
        this.currentPage = this.button1Content - 1;
      } else if (buttonNumber === 2) {
        this.currentPage = this.button2Content - 1;
      } else if (buttonNumber === 3) {
        this.currentPage = this.button3Content - 1;
      }
      this.refreshPage();
    },
    refreshPage() {
      let params;
      let url;
      if (this.filterType !== null) {
        params = {
          page: this.currentPage,
          pageSize: this.pageSize,
          filter: this.filterType
        };
        url = "/RentalObjects/getRentalObjectsFilterPage";
      } else {
        params = {
          page: this.currentPage,
          pageSize: this.pageSize
        };
        url = "/RentalObjects/getRentalObjectsPage";
      }
      axios.get(url, {headers: {
          Authorization: "Bearer " + this.$store.getters.access_token,
        },
        params
      })
          .then(response => {
            this.rentalObjects = response.data.content;
            this.currentPage = response.data.currentPage;
            this.totalPages = response.data.pages;
            this.rentalObjects.forEach(rentalObject => {
              if (rentalObject.photos.length > 0) {
                const index = this.rentalObjects.indexOf(rentalObject);
                this.loadImage(rentalObject.photos[0].photo, index);
              }
            })
          })
          .catch(() =>{
            this.$notify({
              title: "Server error",
              text: "Server is currently off. Please try again later...",
              type: "error"
            });
          });
    }
  },
  computed: {
    button1Content() {
      return this.currentPage === 0 ? this.currentPage+1: this.totalPages - this.currentPage === 1 ? this.currentPage - 1 : this.currentPage;
    },
    button2Content() {
      return this.currentPage === 0 ? this.currentPage+2: this.totalPages - this.currentPage === 1 ? this.currentPage : this.currentPage+1;
    },
    button3Content() {
      return this.currentPage === 0 ? this.currentPage+3: this.totalPages - this.currentPage === 1 ? this.currentPage + 1 : this.currentPage+2;
    },

  }
}
</script>

<style scoped>
  .profile-link {
    color: #008970;
  }

  .rounded-circle {
    background-color:  rgba(176, 184, 180);
    color: white;
    padding: 10px;
  }
</style>
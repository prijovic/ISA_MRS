<template>
  <div class="container-fluid px-4 py-3 rounded">
    <div class="col main pt-3">
      <div class="row">
        <div class="col-1 main"></div>
        <div class="col-4 main">
          <div class="row mb-3" v-bind:style="{color: isNamePresent ? '#3f5b25':'red'}">
            <p class="h6 ps-0 pb-0"><i>Name:</i></p>
            <input class="ps-1 h5" type="text" id="name" v-model="boat.name" placeholder="Input name...">
          </div>
          <div class="row mb-3">
            <p class="h6 ps-0 pb-0"><i>Type:</i></p>
            <input class="ps-1 h5" type="text" id="boatType" v-model="boat.boatType" placeholder="Input type...">
          </div>
          <div class="row mb-3">
            <p class="h6 ps-0 pb-0"><i>Length:</i></p>
            <input class="ps-1 h5" type="number" id="boatLength" v-model="boat.boatLength" placeholder="Input length...">
          </div>
          <div class="row mb-3">
            <p class="h6 ps-0 pb-0"><i>Engine number:</i></p>
            <input class="ps-1 h5" type="text" id="engineNumber" v-model="boat.engineNumber" placeholder="Input engine number...">
          </div>
          <div class="row mb-3">
            <p class="h6 ps-0 pb-0"><i>Engine power:</i></p>
            <input class="ps-1 h5" type="number" id="enginePower" v-model="boat.enginePower" placeholder="Input engine power...">
          </div>
          <div class="row mb-3">
            <p class="h6 ps-0 pb-0"><i>Max speed:</i></p>
            <input class="ps-1 h5" type="text" id="maxSpeed" v-model="boat.maxSpeed" placeholder="Input boat max speed...">
          </div>
          <div class="row mb-3" v-bind:style="{color: isDescriptionPresent ? '#3f5b25':'red'}">
            <p class="h6 ps-0 pb-0"><i>Description:</i></p>
            <textarea class="ps-1 h5" maxlength="255" rows="3" v-model="boat.description" placeholder="Input description..." style="resize: none"></textarea>
          </div>
          <div class="row mb-3">
            <p class="h6 ps-0 pb-0"><i>Photos:</i></p>
            <input class="p-0 h5" type="file" name="photos" accept="image/jpeg" multiple="multiple"/>
            <small class="p-0" style="color: grey;">Acceptable: jpg</small>
          </div>
          <div class="row mb-3">
            <p class="h6 ps-0 pb-0"><i>Capacity:</i></p>
            <input class="ps-1 h5" type="number" v-model="boat.capacity" placeholder="Input capacity...">
          </div>
        </div>
        <div class="col-2 main"></div>
        <div class="col-4 main">
          <div class="row"  v-bind:style="{color:!(isAddressValid || isAddressValid===null)?'red':'#3f5b25'}">
            <p class="h4 p-0"><font-awesome-icon icon="house"></font-awesome-icon> Address</p>
          </div>
          <div class="row mb-3">
            <p class="h6 ps-0 pb-0"><i>Country:</i></p>
            <input class="ps-1 h5" type="text" id="country" v-model="boat.address.country" placeholder="Country">
          </div>
          <div class="row mb-3">
            <p class="h6 ps-0 pb-0"><i>City:</i></p>
            <input class="ps-1 h5" type="text" id="city" v-model="boat.address.city" placeholder="City">
          </div>
          <div class="row mb-3">
            <p class="h6 ps-0 pb-0"><i>Street:</i></p>
            <input class="ps-1 h5" type="text" id="street" v-model="boat.address.street" placeholder="Street">
          </div>
          <div class="row mb-3">
            <p class="h6 ps-0 pb-0"><i>Number:</i></p>
            <input class="ps-1 h5" type="number" id="number" v-model="boat.address.number" placeholder="House Number">
          </div>
          <div class="row mb-3">
            <p class="h6 ps-0 pb-0"><i>Price:</i></p>
            <input class="ps-1 h5" type="number" v-model="boat.price" placeholder="Input price...">
          </div>
          <div class="row mb-3">
            <p class="h6 ps-0 pb-0"><i>Cancellation fee:</i></p>
            <div class="col">
              <select class="form-control" v-model="boat.cancellationFee.feeType" style="width: 100%">
                <option value="Free" selected="selected">Free</option>
                <option value="Fixed">Fixed</option>
                <option value="Percentile">Percentile</option>
              </select>
            </div>
            <div class="col">
              <input class="ps-1 h5" type="number" v-model="boat.cancellationFee.value" placeholder="Input value...">
            </div>
          </div>
          <div class="row mb-3">
            <button type="button" class="btn btn-default" data-bs-toggle="modal" data-bs-target="#staticBackdrop">Additional services</button>
          </div>
          <div class="modal fade" id="staticBackdrop" data-bs-backdrop="static" data-bs-keyboard="false" tabindex="-1" aria-labelledby="Label" aria-hidden="true">
            <div class="modal-dialog">
              <div class="modal-content">
                <div class="modal-header">
                  <h5 class="modal-title" id="Label" style="color: #3f5b25">Additional service</h5>
                  <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                </div>
                <div class="modal-body">
                  <div class="row mb-1" v-for="(additionalService, k) in boat.additionalServices" :key="k">
                    <div class="col">
                      <input type="text" id="additionalServiceName" v-model="additionalService.serviceName" placeholder="Input name">
                    </div>
                    <div class="col">
                      <input type="number" id="additionalServicePrice" v-model="additionalService.servicePrice" placeholder="Input price">
                    </div>
                    <div class="col">
                    <span>
                      <font-awesome-icon icon="minus-circle" @click="removeService(k)" v-show="k || ( !k && boat.additionalServices.length > 1)" style="color: red"></font-awesome-icon>
                      <font-awesome-icon icon="plus-circle" @click="addService()" v-show="k === boat.additionalServices.length-1" style="color: #378220"></font-awesome-icon>
                    </span>
                    </div>
                  </div>
                </div>
                <div class="modal-footer"></div>
              </div>
            </div>
          </div>
          <div class="row mb-3">
            <button type="button" class="btn btn-default" data-bs-toggle="modal" style="width: 100%" data-bs-target="#staticBackdrop1">Rules of conduct</button>
          </div>
          <div class="modal fade" id="staticBackdrop1" data-bs-backdrop="static" data-bs-keyboard="false" tabindex="-1" aria-labelledby="Label" aria-hidden="true">
            <div class="modal-dialog">
              <div class="modal-content">
                <div class="modal-header">
                  <h5 class="modal-title" id="Label1" style="color: #3f5b25">Rules of conduct</h5>
                  <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                </div>
                <div class="modal-body">
                  <div class="row mb-1" v-for="(conductRule, k) in boat.conductRules" :key="k">
                    <div class="col">
                      <input type="text" id="Rule" v-model="conductRule.rule" placeholder="Name">
                    </div>
                    <div class="col">
                      <select class="form-control" v-model="conductRule.conductType" style="width: 100%">
                        <option value="Do" selected="selected">Allowed</option>
                        <option value="DoNot">Not allowed</option>
                      </select>
                    </div>
                    <div class="col">
                    <span>
                      <font-awesome-icon icon="minus-circle" @click="removeRule(k)" v-show="k || ( !k && boat.conductRules.length > 1)" style="color: red"></font-awesome-icon>
                      <font-awesome-icon icon="plus-circle" @click="addRule()" v-show="k === boat.conductRules.length-1" style="color: #378220"></font-awesome-icon>
                    </span>
                    </div>
                  </div>
                </div>
                <div class="modal-footer"></div>
              </div>
            </div>
          </div>
          <div class="row mb-3">
            <button type="button" class="btn btn-default" data-bs-toggle="modal" data-bs-target="#staticBackdrop3">Navigation equipment</button>
          </div>
          <div class="modal fade" id="staticBackdrop3" data-bs-backdrop="static" data-bs-keyboard="false" tabindex="-1" aria-labelledby="Label" aria-hidden="true">
            <div class="modal-dialog">
              <div class="modal-content">
                <div class="modal-header">
                  <h5 class="modal-title" id="Label3" style="color: #3f5b25">Navigation equipment</h5>
                  <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                </div>
                <div class="modal-body">
                  <div class="row mb-1" v-for="(navigationEquipment, k) in boat.navigationEquipments" :key="k">
                    <div class="col">
                      <input type="number" id="navigationEquipment" v-model="navigationEquipment.navigationName" placeholder="Enter navigation equipment...">
                    </div>
                    <div class="col">
                    <span>
                      <font-awesome-icon icon="minus-circle" @click="removeNavigationEquipment(k)" v-show="k || ( !k && boat.navigationEquipments.length > 1)" style="color: red"></font-awesome-icon>
                      <font-awesome-icon icon="plus-circle" @click="addNavigationEquipment()" v-show="k === boat.navigationEquipments.length-1" style="color: #378220"></font-awesome-icon>
                    </span>
                    </div>
                  </div>
                </div>
                <div class="modal-footer"></div>
              </div>
            </div>
          </div>
          <div class="row mb-3">
            <button type="button" class="btn btn-default" data-bs-toggle="modal" data-bs-target="#staticBackdrop3">Fishing equipment</button>
          </div>
          <div class="modal fade" id="staticBackdrop4" data-bs-backdrop="static" data-bs-keyboard="false" tabindex="-1" aria-labelledby="Label" aria-hidden="true">
            <div class="modal-dialog">
              <div class="modal-content">
                <div class="modal-header">
                  <h5 class="modal-title" id="Label4" style="color: #3f5b25">Fishing equipment</h5>
                  <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                </div>
                <div class="modal-body">
                  <div class="row mb-1" v-for="(fishingEquipment, k) in boat.fishingEquipments" :key="k">
                    <div class="col">
                      <input type="number" id="fishingEquipment" v-model="fishingEquipment.fishingEquipmentName" placeholder="Enter navigation equipment...">
                    </div>
                    <div class="col">
                    <span>
                      <font-awesome-icon icon="minus-circle" @click="removeFishingEquipment(k)" v-show="k || ( !k && boat.fishingEquipments.length > 1)" style="color: red"></font-awesome-icon>
                      <font-awesome-icon icon="plus-circle" @click="addFishingEquipment()" v-show="k === boat.fishingEquipments.length-1" style="color: #378220"></font-awesome-icon>
                    </span>
                    </div>
                  </div>
                </div>
                <div class="modal-footer"></div>
              </div>
            </div>
          </div>
          <div class="row main pt-3">
            <div class="col-3"></div>
            <div class="col-9">
              <button type="button" @click="validateInputs" class="btn btn-lg">Add new boat</button>
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import {FontAwesomeIcon} from "@fortawesome/vue-fontawesome";
import { library } from "@fortawesome/fontawesome-svg-core";
import { faHouse, faMinusCircle, faPlusCircle } from "@fortawesome/free-solid-svg-icons";
//import axios from "axios/index";
import store from "@/store";
import axios from "axios";

library.add(faHouse, faMinusCircle, faPlusCircle);

export default {
  name: "BoatAdditionForm",
  components: {
    FontAwesomeIcon,
  },
  data(){
    return{
      boat: {
        name: null,
        boatType: null,
        boatLength: null,
        engineNumber: null,
        enginePower: null,
        maxSpeed: null,
        boatOwnerEmail: null,
        description: null,
        photos: [],
        capacity: null,
        price: null,
        serviceName: null,
        servicePrice:null,
        additionalServices: [
          {
            serviceName: null,
            servicePrice:null
          }
        ],
        conductRules: [
          {
            rule: null,
            conductType: null
          }
        ],
        cancellationFee: {
          feeType: null,
          value: null
        },
        address: {
          street: null,
          number: null,
          city: null,
          country: null,
          latitude: null,
          longitude: null
        },
        navigationEquipments: [
          {
            navigationName: null
          }
        ],
        fishingEquipments: [
          {
            fishingEquipmentName: null
          }
        ]
      }
    }
  },
  mounted() {
    this.boat.boatOwnerEmail = store.state.email
  },
  methods: {
    addVacationRental() {
      axios.post("/RentalObjects/addBoat", null, {
        headers: {
          Authorization: "Bearer " + this.accessToken
        },
        params: {
          name: this.name,
          boatType: this.boatType,
          boatLength: this.boatLength,
          engineNumber: this.engineNumber,
          enginePower: this.enginePower,
          maxSpeed: this.maxSpeed,
          ownerEmail: this.ownerEmail,
          description: this.description,
          photos: this.photos,
          capacity: this.capacity,
          price: this.price,
          additionalServices: [
            {
              serviceName: this.serviceName,
              servicePrice: this.servicePrice
            }
          ],
          conductRules: [
            {
              rule: this.rule,
              conductType: this.conductType
            }
          ],
          cancellationFee: {
            feeType: this.feeType,
            value: this.value
          },
          address: {
            country: this.country,
            city: this.city,
            street: this.street,
            number: this.number,
            latitude: this.latitude,
            longitude: this.longitude
          },
          navigationEquipments: [
            {
              navigationName: this.navigationName
            }
          ],
          fishingEquipment: [
            {
              fishingEquipmentName: this.fishingEquipmentName
            }
          ]
        }
      })
          .then(() => {
            this.$notify({
              title: "Adding vacation rental Notification",
              text: "New vacation rental successfully added.",
              position: "bottom right",
              type: "success"
            });
          })
          .catch((error) => {
            if (error.response.status === 404) {
              this.$notify({
                title: "User not found",
                text: "User with the specified e-mail was not found!",
                type: "warn"
              })
            } else if (error.response.status === 400) {
              this.$notify({
                title: "Invalid Request Status",
                text: "Request status does not have the right form!",
                type: "warn"
              })
            } else if (error.response.status === 500) {
              this.$notify({
                title: "Internal Server Error",
                text: "Something went wrong on the server! Please try again later.",
                type: "error"
              })
            }
          })
    },
    addService() {
      this.boat.additionalServices.push({ serviceName: null, servicePrice: null });
    },
    removeService(index) {
      this.boat.additionalServices.splice(index, 1);
    },
    addRule() {
      this.boat.conductRules.push({ rule: null, conductType: null });
    },
    removeRule(index) {
      this.boat.conductRules.splice(index, 1);
    },
    addNavigationEquipment() {
      this.boat.navigationEquipments.push({ navigationName: null});
    },
    removeNavigationEquipment(index) {
      this.boat.navigationEquipments.splice(index, 1);
    },
    addFishingEquipment() {
      this.boat.fishingEquipments.push({ fishingEquipmentName: null});
    },
    removeFishingEquipment(index) {
      this.boat.fishingEquipments.splice(index, 1);
    },
    validateInputs() {
      this.validateAddress();
      this.isInputPresent();
      if(this.allInputsPresent && this.isAddressValid) this.addVacationRental();
    },
    isInputPresent() {
      this.isNamePresent = this.boat.name;
      this.isDescriptionPresent = this.boat.description;
      this.allInputsPresent = !!(this.isNamePresent && this.isDescriptionPresent);
    },
    validateAddress() {
      const apiKey = 'VrDrl5BjEA0Whvb-chHbFz96HV4qlCXB-yoiTRRLKno';
      const url = 'https://geocoder.ls.hereapi.com/6.2/geocode.json' +
          '?apiKey=' + apiKey +
          '&housenumber=' + this.boat.address.number +
          '&street=' + this.boat.address.street +
          '&city=' + this.boat.address.city +
          '&country=' + this.boat.address.country;
      fetch(url)
          .then(response => response.json())
          .then(data => {
            const responseView = data.Response.View;
            if (responseView.length === 0) {
              console.log("NEMA ADRESE!")
              this.isAddressValid = false;
            } else {
              const location = responseView[0].Result[0].Location.DisplayPosition;
              const address = responseView[0].Result[0].Location.Address;
              this.boat.address.city = address.City;
              this.boat.address.country = address.AdditionalData[0].value;
              this.boat.address.street = address.Street;
              this.boat.address.longitude = location.Longitude;
              this.boat.address.latitude = location.Latitude;
              console.log(this.boat.address);
              this.isAddressValid = true;
            }
          })
          .catch(error => {
            console.log(error);
            this.isAddressValid = false;
          });
    }
  },
  computed: {
    accessToken(){
      return store.state.access_token;
    }
  }
}
</script>

<style scoped>
.container-fluid {
  outline: solid 2px #3f5b25;
  margin: 10px;
  color: #3f5b25;
}
input[type='text'], input[type='number'] {
  width: 100%;
  font-weight: 100;
}

input::placeholder {
  color: grey;
  font-weight: 100;
}
</style>
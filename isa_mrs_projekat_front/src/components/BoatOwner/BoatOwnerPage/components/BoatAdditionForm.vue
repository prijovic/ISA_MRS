<template>
  <div class="container-fluid px-4 py-3 rounded">
    <div class="col main pt-3">
      <div class="row">
        <div class="col-1 main"></div>
        <div class="col-4 main">
          <div class="row mb-3">
            <p class="h6 ps-0 pb-0"><i>Name:</i></p>
            <input class="ps-1 h5" type="text" id="name" v-model="boat.name" placeholder="Input name...">
            <p class="small" style="color:red" v-if='!nameIsEntered'>'Name' is a mandatory field.</p>
          </div>
          <div class="row mb-3">
            <p class="h6 ps-0 pb-0"><i>Type:</i></p>
            <input class="ps-1 h5" type="text" id="boatType" v-model="boat.boatType" placeholder="Input type...">
            <p class="small" style="color:red" v-if='!boatTypeIsEntered'>'Type' is a mandatory field.</p>
          </div>
          <div class="row mb-3">
            <p class="h6 ps-0 pb-0"><i>Length:</i></p>
            <input class="ps-1 h5" type="number" id="boatLength" v-model="boat.boatLength" placeholder="Input length...">
            <p class="small" style="color:red" v-if='!boatLengthIsEntered'>'Length' is a mandatory field.</p>
          </div>
          <div class="row mb-3">
            <p class="h6 ps-0 pb-0"><i>Engine number:</i></p>
            <input class="ps-1 h5" type="text" id="engineNumber" v-model="boat.engineNumber" placeholder="Input engine number...">
            <p class="small" style="color:red" v-if='!engineNumberIsEntered'>'Engine number' is a mandatory field.</p>
          </div>
          <div class="row mb-3">
            <p class="h6 ps-0 pb-0"><i>Engine power:</i></p>
            <input class="ps-1 h5" type="number" id="enginePower" v-model="boat.enginePower" placeholder="Input engine power...">
            <p class="small" style="color:red" v-if='!enginePowerIsEntered'>'Engine power' is a mandatory field.</p>
          </div>
          <div class="row mb-3">
            <p class="h6 ps-0 pb-0"><i>Max speed:</i></p>
            <input class="ps-1 h5" type="text" id="maxSpeed" v-model="boat.maxSpeed" placeholder="Input boat max speed...">
            <p class="small" style="color:red" v-if='!capacityIsEntered'>'Capacity' is a mandatory field.</p>
          </div>
          <div class="row mb-3">
            <p class="h6 ps-0 pb-0"><i>Description:</i></p>
            <textarea class="ps-1 h5" maxlength="255" rows="3" v-model="boat.description" placeholder="Input description..." style="resize: none"></textarea>
            <p class="small" style="color:red" v-if='!descriptionIsEntered'>'Description' is a mandatory field.</p>
          </div>
          <div class="row mb-3">
            <p class="h6 ps-0 pb-0"><i>Photos:</i></p>
            <input class="p-0 h5" type="file" name="photos" accept="image/jpeg" multiple="multiple"/>
            <small class="p-0" style="color: grey;">Acceptable: jpg</small>
          </div>
          <div class="row mb-3">
            <p class="h6 ps-0 pb-0"><i>Capacity:</i></p>
            <input class="ps-1 h5" type="number" v-model="boat.capacity" placeholder="Input capacity...">
            <p class="small" style="color:red" v-if='!capacityIsEntered'>'Capacity' is a mandatory field.</p>
          </div>
        </div>
        <div class="col-2 main"></div>
        <div class="col-4 main">
          <div class="row">
            <p class="h4 p-0"><font-awesome-icon icon="house"></font-awesome-icon> Address</p>
          </div>
          <div class="row mb-3">
            <p class="h6 ps-0 pb-0"><i>Country:</i></p>
            <input class="ps-1 h5" type="text" id="country" v-model="boat.address.country" placeholder="Country">
            <p class="small" style="color:red" v-if='!countryIsEntered'>'Country' is a mandatory field.</p>
            <p class="small" style="color:red" v-if='!addressIsValid'>Invalid address data.</p>
          </div>
          <div class="row mb-3">
            <p class="h6 ps-0 pb-0"><i>City:</i></p>
            <input class="ps-1 h5" type="text" id="city" v-model="boat.address.city" placeholder="City">
            <p class="small" style="color:red" v-if='!cityIsEntered'>'City' is a mandatory field.</p>
            <p class="small" style="color:red" v-if='!addressIsValid'>Invalid address data.</p>
          </div>
          <div class="row mb-3">
            <p class="h6 ps-0 pb-0"><i>Street:</i></p>
            <input class="ps-1 h5" type="text" id="street" v-model="boat.address.street" placeholder="Street">
            <p class="small" style="color:red" v-if='!streetIsEntered'>'Street' is a mandatory field.</p>
            <p class="small" style="color:red" v-if='!addressIsValid'>Invalid address data.</p>
          </div>
          <div class="row mb-3">
            <p class="h6 ps-0 pb-0"><i>Number:</i></p>
            <input class="ps-1 h5" type="number" id="number" v-model="boat.address.number" placeholder="House Number">
            <p class="small" style="color:red" v-if='!numberIsEntered'>'Number' is a mandatory field.</p>
            <p class="small" style="color:red" v-if='!addressIsValid'>Invalid address data.</p>
          </div>
          <div class="row mb-3">
            <p class="h6 ps-0 pb-0"><i>Price:</i></p>
            <input class="ps-1 h5" type="number" v-model="boat.price" placeholder="Input price...">
            <p class="small" style="color:red" v-if='!priceIsEntered'>'Price' is a mandatory field.</p>
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
                      <input type="text" id="navigationEquipment" v-model="navigationEquipment.navigationName" placeholder="Enter navigation equipment...">
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
            <button type="button" class="btn btn-default" data-bs-toggle="modal" data-bs-target="#staticBackdrop4">Fishing equipment</button>
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
                      <input type="text" id="fishingEquipment" v-model="fishingEquipment.fishingEquipmentName" placeholder="Enter navigation equipment...">
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
              <button type="button" @click="submit" class="btn btn-lg">Add new boat</button>
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
import axios from "axios/index";
import store from "@/store";

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
      },
      nameIsEntered: true,
      boatTypeIsEntered: true,
      boatLengthIsEntered: true,
      engineNumberIsEntered: true,
      enginePowerIsEntered: true,
      maxSpeedIsEntered: true,
      descriptionIsEntered: true,
      priceIsEntered: true,
      capacityIsEntered: true,
      countryIsEntered: true,
      cityIsEntered: true,
      streetIsEntered: true,
      numberIsEntered: true,
      addressIsValid: true
    }
  },
  mounted() {
    this.boat.boatOwnerEmail = store.state.email
  },
  computed: {
    accessToken() {
      return store.state.access_token;
    },
    isNameEntered() {
      return Boolean(this.boat.name);
    },
    isBoatTypeEntered() {
      return Boolean(this.boat.boatType);
    },
    isBoatLengthEntered() {
      return Boolean(this.boat.boatLength);
    },
    isEngineNumberEntered() {
      return Boolean(this.boat.engineNumber);
    },
    isEnginePowerEntered() {
      return Boolean(this.boat.enginePower);
    },
    isMaxSpeedEntered() {
      return Boolean(this.boat.maxSpeed);
    },
    isDescriptionEntered() {
      return Boolean(this.boat.description);
    },
    isCountryEntered() {
      return Boolean(this.boat.address.country);
    },
    isCityEntered() {
      return Boolean(this.boat.address.city);
    },
    isStreetEntered() {
      return Boolean(this.boat.address.street);
    },
    isNumberEntered() {
      return Boolean(this.boat.address.number);
    }
  },
  methods: {
    submit() {
      if (this.isDataEntered() && this.isDataCorrect()) {
        this.addBoat();
      }
    },
    addBoat() {
      axios.post("/RentalObjects/addBoat", this.boat, {
        headers: {
          Authorization: "Bearer " + this.accessToken
        },
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
    isDataEntered() {
      if (!this.isNameEntered) {
        this.nameIsEntered = false;
        return false;
      }
      if (!this.isBoatTypeEntered) {
        this.boatTypeIsEntered = false;
        return false;
      }
      if (!this.isBoatLengthEntered) {
        this.boatLengthIsEntered = false;
        return false;
      }
      if (!this.isEngineNumberEntered) {
        this.engineNumberIsEntered = false;
        return false;
      }
      if (!this.isEnginePowerEntered) {
        this.enginePowerIsEntered = false;
        return false;
      }
      if (!this.isMaxSpeedEntered) {
        this.maxSpeedIsEntered - false;
        return false;
      }
      if (!this.isDescriptionEntered) {
        this.descriptionIsEntered = false;
        return false;
      }
      if (!this.isCountryEntered) {
        this.countryIsEntered = false;
        return false;
      }
      if (!this.isCityEntered) {
        this.cityIsEntered = false;
        return false;
      }
      if (!this.isStreetEntered) {
        this.streetIsEntered = false;
        return false;
      }
      if (!this.isNumberEntered) {
        this.numberIsEntered = false;
        return false;
      }
      return true;
    },
    isDataCorrect() {
      this.validateAddress();
      if (!this.addressIsValid) {
        return false;
      }
      return true;
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
              this.addressIsValid = false;
            } else {
              const location = responseView[0].Result[0].Location.DisplayPosition;
              const address = responseView[0].Result[0].Location.Address;
              this.boat.address.city = this.transliterate(address.City);
              this.boat.address.country = this.transliterate(address.AdditionalData[0].value);
              this.boat.address.street = this.transliterate(address.Street);
              this.boat.address.longitude = location.Longitude;
              this.boat.address.latitude = location.Latitude;
              this.addressIsValid = true;
            }
          })
          .catch(() => {
            this.addressIsValid = false;
          });
    },
    transliterate(word) {
      let letters = {"Б":"B", "В":"V", "Г":"G", "Д":"D", "Ђ":"Đ", "Ж":"Ž", "З":"Z", "И":"I", "К":"K", "Л":"L", "Љ":"Lj", "М":"M", "Н":"N", "Њ":"Nj", "П":"P",
        "Р":"R", "С":"S", "Ћ":"Ć", "У":"U", "Ф":"F", "Х":"H", "Ц":"C", "Ч":"Č", "Џ":"Dž", "Ш":"Š", "б":"b", "в":"v", "г":"g", "д":"d", "ђ":"đ", "ж":"ž", "з":"z", "и":"i", "к":"k", "л":"l", "љ":"lj", "м":"m", "н":"n", "њ":"nj", "п":"p",
        "р":"r", "с":"s", "т":"t", "ћ":"ć", "у":"u", "ф":"f", "х":"h", "ц":"c", "ч":"č", "џ":"dž", "ш":"š"};
      return word.split('').map(function (char) {
        return letters[char] || char;
      }).join("");
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
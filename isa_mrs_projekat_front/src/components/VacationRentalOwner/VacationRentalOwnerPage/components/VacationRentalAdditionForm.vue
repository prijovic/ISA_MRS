<template>
  <div class="container-fluid px-4 py-3 rounded">
  <div class="col main pt-3">
    <div class="row">
      <div class="col-1 main"></div>
      <div class="col-4 main">
        <div class="row mb-3">
          <p class="h6 ps-0 pb-0"><i>Name:</i></p>
          <input class="ps-1 h5" type="text" id="name" v-model="vacationRental.name" placeholder="Input name...">
          <p class="small" style="color:red" v-if='!nameIsEntered'>'Name' is a mandatory field.</p>
        </div>
        <div class="row">
          <p class="h4 p-0"><font-awesome-icon icon="house"></font-awesome-icon> Address</p>
        </div>
        <div class="row mb-3">
          <p class="h6 ps-0 pb-0"><i>Country:</i></p>
          <input class="ps-1 h5" type="text" id="country" v-model="vacationRental.address.country" placeholder="Country">
          <p class="small" style="color:red" v-if='!countryIsEntered'>'Country' is a mandatory field.</p>
          <p class="small" style="color:red" v-if='!addressIsValid'>Invalid address data.</p>
        </div>
        <div class="row mb-3">
          <p class="h6 ps-0 pb-0"><i>City:</i></p>
          <input class="ps-1 h5" type="text" id="city" v-model="vacationRental.address.city" placeholder="City">
          <p class="small" style="color:red" v-if='!cityIsEntered'>'City' is a mandatory field.</p>
          <p class="small" style="color:red" v-if='!addressIsValid'>Invalid address data.</p>
        </div>
        <div class="row mb-3">
          <p class="h6 ps-0 pb-0"><i>Street:</i></p>
          <input class="ps-1 h5" type="text" id="street" v-model="vacationRental.address.street" placeholder="Street">
          <p class="small" style="color:red" v-if='!streetIsEntered'>'Street' is a mandatory field.</p>
          <p class="small" style="color:red" v-if='!addressIsValid'>Invalid address data.</p>
        </div>
        <div class="row mb-3">
          <p class="h6 ps-0 pb-0"><i>Number:</i></p>
          <input class="ps-1 h5" type="number" id="number" v-model="vacationRental.address.number" placeholder="House Number">
          <p class="small" style="color:red" v-if='!numberIsEntered'>'Number' is a mandatory field.</p>
          <p class="small" style="color:red" v-if='!addressIsValid'>Invalid address data.</p>
        </div>
        <div class="row mb-3">
          <p class="h6 ps-0 pb-0"><i>Description:</i></p>
          <textarea class="ps-1 h5" maxlength="255" rows="3" v-model="vacationRental.description" placeholder="Input description..." style="resize: none"></textarea>
          <p class="small" style="color:red" v-if='!descriptionIsEntered'>'Description' is a mandatory field.</p>
        </div>
        <div class="row mb-3">
          <p class="h6 ps-0 pb-0"><i>Photos:</i></p>
          <input class="p-0 h5" type="file" name="photos" accept="image/jpeg" multiple="multiple"/>
          <small class="p-0" style="color: grey;">Acceptable: jpg</small>
        </div>
      </div>
      <div class="col-2 main"></div>
      <div class="col-4 main">
        <div class="row mb-3">
          <p class="h6 ps-0 pb-0"><i>Capacity:</i></p>
          <input class="ps-1 h5" type="number" v-model="vacationRental.capacity" placeholder="Input capacity...">
          <p class="small" style="color:red" v-if='!capacityIsEntered'>'Capacity' is a mandatory field.</p>
        </div>
        <div class="row mb-3">
          <p class="h6 ps-0 pb-0"><i>Price:</i></p>
          <input class="ps-1 h5" type="number" v-model="vacationRental.price" placeholder="Input price...">
          <p class="small" style="color:red" v-if='!priceIsEntered'>'Price' is a mandatory field.</p>
        </div>
        <div class="row mb-3">
          <p class="h6 ps-0 pb-0"><i>Cancellation fee:</i></p>
          <div class="col">
            <select class="form-control" v-model="vacationRental.cancellationFee.feeType" style="width: 100%">
              <option value="Free" selected="selected">Free</option>
              <option value="Fixed">Fixed</option>
              <option value="Percentile">Percentile</option>
            </select>
          </div>
          <div class="col">
            <input class="ps-1 h5" type="number" v-model="vacationRental.cancellationFee.value" placeholder="Input value...">
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
                <div class="row mb-1" v-for="(additionalService, k) in vacationRental.additionalServices" :key="k">
                  <div class="col">
                    <input type="text" id="additionalServiceName" v-model="additionalService.serviceName" placeholder="Input name">
                  </div>
                  <div class="col">
                    <input type="number" id="additionalServicePrice" v-model="additionalService.servicePrice" placeholder="Input price">
                  </div>
                  <div class="col">
                    <span>
                      <font-awesome-icon icon="minus-circle" @click="removeService(k)" v-show="k || ( !k && vacationRental.additionalServices.length > 1)" style="color: red"></font-awesome-icon>
                      <font-awesome-icon icon="plus-circle" @click="addService()" v-show="k === vacationRental.additionalServices.length-1" style="color: #378220"></font-awesome-icon>
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
                <div class="row mb-1" v-for="(conductRule, k) in vacationRental.conductRules" :key="k">
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
                      <font-awesome-icon icon="minus-circle" @click="removeRule(k)" v-show="k || ( !k && vacationRental.conductRules.length > 1)" style="color: red"></font-awesome-icon>
                      <font-awesome-icon icon="plus-circle" @click="addRule()" v-show="k === vacationRental.conductRules.length-1" style="color: #378220"></font-awesome-icon>
                    </span>
                  </div>
                </div>
              </div>
              <div class="modal-footer"></div>
            </div>
          </div>
        </div>
        <div class="row mb-3">
          <button type="button" class="btn btn-default" data-bs-toggle="modal" data-bs-target="#staticBackdrop3">Beds per room</button>
        </div>
        <div class="modal fade" id="staticBackdrop3" data-bs-backdrop="static" data-bs-keyboard="false" tabindex="-1" aria-labelledby="Label" aria-hidden="true">
          <div class="modal-dialog">
            <div class="modal-content">
              <div class="modal-header">
                <h5 class="modal-title" id="Label3" style="color: #3f5b25">Number of beds per room</h5>
                <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
              </div>
              <div class="modal-body">
                <div class="row mb-1" v-for="(room, k) in vacationRental.rooms" :key="k">
                  <div class="col">
                    <input type="number" id="bedsPerRoom" v-model="room.beds" placeholder="Enter number of beds...">
                  </div>
                  <div class="col">
                    <span>
                      <font-awesome-icon icon="minus-circle" @click="removeRooms(k)" v-show="k || ( !k && vacationRental.rooms.length > 1)" style="color: red"></font-awesome-icon>
                      <font-awesome-icon icon="plus-circle" @click="addRooms()" v-show="k === vacationRental.rooms.length-1" style="color: #378220"></font-awesome-icon>
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
          <div class="col-8">
            <button type="button" class="btn btn-lg" @click="submit">Add vacation rental</button>
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
  name: "RentalObjectAdditionForm",
  components: {
    FontAwesomeIcon,
  },
  data() {
    return{
      vacationRental: {
        name: null,
        ownerEmail: null,
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
        rooms: [
          {
            beds: null
          }
        ]
      },
      nameIsEntered: true,
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
    this.vacationRental.ownerEmail = store.state.email
  },
  computed: {
    accessToken() {
      return store.state.access_token;
    },
    isNameEntered() {
      return Boolean(this.vacationRental.name);
    },
    isDescriptionEntered() {
      return Boolean(this.vacationRental.description);
    },
    isPriceEntered() {
      return Boolean(this.vacationRental.price);
    },
    isCapacityEntered() {
      return Boolean(this.vacationRental.capacity);
    },
    isCountryEntered() {
      return Boolean(this.vacationRental.address.country);
    },
    isCityEntered() {
      return Boolean(this.vacationRental.address.city);
    },
    isStreetEntered() {
      return Boolean(this.vacationRental.address.street);
    },
    isNumberEntered() {
      return Boolean(this.vacationRental.address.number);
    }
  },
  methods: {
    submit() {
      if (this.isDataEntered() && this.isDataCorrect()) {
        this.addVacationRental();
      }
    },
    addVacationRental(){
      axios.post("/RentalObjects/addVacationRental", this.vacationRental, {
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
      .catch((error) =>{
        if(error.response.status===404){
          this.$notify({
            title: "User not found",
            text: "User with the specified e-mail was not found!",
            type: "warn"
          })
        } else if(error.response.status===400) {
          this.$notify({
            title: "Invalid Request Status",
            text: "Request status does not have the right form!",
            type: "warn"
          })
        } else if(error.response.status===500){
          this.$notify({
            title: "Internal Server Error",
            text:"Something went wrong on the server! Please try again later.",
            type: "error"
          })
        }
      })
    },
    addService() {
      this.vacationRental.additionalServices.push({ serviceName: null, servicePrice: null });
    },
    removeService(index) {
      this.vacationRental.additionalServices.splice(index, 1);
    },
    addRule() {
      this.vacationRental.conductRules.push({ rule: null, conductType: null });
    },
    removeRule(index) {
      this.vacationRental.conductRules.splice(index, 1);
    },
    addRooms() {
      this.vacationRental.rooms.push({ beds: null});
    },
    removeRooms(index) {
      this.vacationRental.rooms.splice(index, 1);
    },
    isDataEntered() {
      if (!this.isNameEntered) {
        this.nameIsEntered = false;
        return false;
      }
      if (!this.isDescriptionEntered) {
        this.descriptionIsEntered = false;
        return false;
      }
      if (!this.isPriceEntered) {
        this.priceIsEntered = false;
        return false;
      }
      if (!this.isCapacityEntered) {
        this.capacityIsEntered = false;
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
          '&housenumber=' + this.vacationRental.address.number +
          '&street=' + this.vacationRental.address.street +
          '&city=' + this.vacationRental.address.city +
          '&country=' + this.vacationRental.address.country;
      fetch(url)
          .then(response => response.json())
          .then(data => {
            const responseView = data.Response.View;
            if (responseView.length === 0) {
              this.addressIsValid = false;
            }
            else {
              const location = responseView[0].Result[0].Location.DisplayPosition;
              const address = responseView[0].Result[0].Location.Address;
              this.vacationRental.address.city = this.transliterate(address.City);
              this.vacationRental.address.country = this.transliterate(address.AdditionalData[0].value);
              this.vacationRental.address.street = this.transliterate(address.Street);
              this.vacationRental.address.longitude = location.Longitude;
              this.vacationRental.address.latitude = location.Latitude;
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

.btn {
  margin-top: 15px;
  background-color: #378220;
  color: #f7f7f2;
}

.btn:active, .btn:hover, .btn:focus {
  background-color: #f7f7f2;
  color: #378220;
  border: 1px solid #3F9725;
}
</style>
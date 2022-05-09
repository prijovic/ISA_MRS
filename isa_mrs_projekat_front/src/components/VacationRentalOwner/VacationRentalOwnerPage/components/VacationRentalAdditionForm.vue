<template>
  <div class="col main pt-3">
    <div class="row">
      <div class="col-1 main"></div>
      <div class="col-4 main">
        <div class="row mb-3">
          <p class="h6 ps-0 pb-0"><i>Name:</i></p>
          <input class="ps-1 h5" type="text" id="name" v-model="vacationRental.name" placeholder="Input name...">
        </div>
        <div class="row">
          <p class="h4 p-0"><font-awesome-icon icon="house"></font-awesome-icon> Address</p>
        </div>
        <div class="row mb-3">
          <p class="h6 ps-0 pb-0"><i>Country:</i></p>
          <input class="ps-1 h5" type="text" id="country" v-model="vacationRental.address.country" placeholder="Country">
        </div>
        <div class="row mb-3">
          <p class="h6 ps-0 pb-0"><i>City:</i></p>
          <input class="ps-1 h5" type="text" id="city" v-model="vacationRental.address.city" placeholder="City">
        </div>
        <div class="row mb-3">
          <p class="h6 ps-0 pb-0"><i>Street:</i></p>
          <input class="ps-1 h5" type="text" id="street" v-model="vacationRental.address.street" placeholder="Street">
        </div>
        <div class="row mb-3">
          <p class="h6 ps-0 pb-0"><i>Number:</i></p>
          <input class="ps-1 h5" type="number" id="number" v-model="vacationRental.address.number" placeholder="House Number">
        </div>
        <div class="row mb-3">
          <p class="h6 ps-0 pb-0"><i>Description:</i></p>
          <textarea class="ps-1 h5" maxlength="255" rows="3" v-model="vacationRental.description" placeholder="Input description..." style="resize: none"></textarea>
        </div>
        <div class="row mb-3">
          <p class="h6 ps-0 pb-0"><i>Photos:</i></p>
          <input class="p-0 h5" type="file" name="photos" @change="onFileSelected" accept="image/jpeg" multiple="multiple"/>
          <small class="p-0" style="color: grey;">Acceptable: jpg</small>
        </div>
      </div>
      <div class="col-2 main"></div>
      <div class="col-4 main">
        <div class="row mb-3">
          <p class="h6 ps-0 pb-0"><i>Capacity:</i></p>
          <input class="ps-1 h5" type="number" v-model="vacationRental.capacity" placeholder="Input capacity...">
        </div>
        <div class="row mb-3">
          <p class="h6 ps-0 pb-0"><i>Price:</i></p>
          <input class="ps-1 h5" type="number" placeholder="Input price...">
        </div>
        <div class="row mb-3">
          <p class="h6 ps-0 pb-0"><i>Cancellation fee:</i></p>
          <div class="col-6">
            <select class="form-control" v-model="vacationRental.cancellationFee.feeType" style="width: 100%">
              <option value="Free" selected="selected">Free</option>
              <option value="Fixed">Fixed</option>
              <option value="Percentile">Percentile</option>
            </select>
          </div>
          <div class="col-6">
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
                    <input type="text" id="additionalServiceName" v-model="additionalService.name" placeholder="Name">
                  </div>
                  <div class="col">
                    <input type="number" id="additionalServicePrice" v-model="additionalService.price" placeholder="Price">
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
          <p class="h6 ps-0 pb-0"><i>Rules of conduct:</i></p>
          <div class="col-6">
            <button type="button" class="btn btn-default" data-bs-toggle="modal" style="width: 100%" data-bs-target="#staticBackdrop1">Allowed</button>
          </div>
          <div class="modal fade" id="staticBackdrop1" data-bs-backdrop="static" data-bs-keyboard="false" tabindex="-1" aria-labelledby="Label" aria-hidden="true">
            <div class="modal-dialog">
              <div class="modal-content">
                <div class="modal-header">
                  <h5 class="modal-title" id="Label1" style="color: #3f5b25">Allowed rules of conduct</h5>
                  <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                </div>
                <div class="modal-body">
                  <div class="row mb-1" v-for="(conductRule, k) in vacationRental.allowedConductRules" :key="k">
                    <input type="text" id="allowedRule" v-model="conductRule.rule" placeholder="Name">
                    <div class="col">
                    <span>
                      <font-awesome-icon icon="minus-circle" @click="removeAllowedRule(k)" v-show="k || ( !k && vacationRental.allowedConductRules.length > 1)" style="color: red"></font-awesome-icon>
                      <font-awesome-icon icon="plus-circle" @click="addAllowedRule()" v-show="k === vacationRental.allowedConductRules.length-1" style="color: #378220"></font-awesome-icon>
                    </span>
                    </div>
                  </div>
                </div>
                <div class="modal-footer"></div>
              </div>
            </div>
          </div>
          <div class="col-6">
          <button type="button" class="btn btn-default" data-bs-toggle="modal" style="width: 100%" data-bs-target="#staticBackdrop2">Illicit</button>
          </div>
          <div class="modal fade" id="staticBackdrop2" data-bs-backdrop="static" data-bs-keyboard="false" tabindex="-1" aria-labelledby="Label" aria-hidden="true">
            <div class="modal-dialog">
              <div class="modal-content">
                <div class="modal-header">
                  <h5 class="modal-title" id="Label2" style="color: #3f5b25">Illicit rules of conduct</h5>
                  <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                </div>
                <div class="modal-body">
                  <div class="row mb-1" v-for="(conductRule, k) in vacationRental.illicitConductRules" :key="k">
                    <input type="text" id="rule" v-model="conductRule.rule" placeholder="Please enter a rule...">
                    <div class="col">
                    <span>
                      <font-awesome-icon icon="minus-circle" @click="removeIllicitRule(k)" v-show="k || ( !k && vacationRental.illicitConductRules.length > 1)" style="color: red"></font-awesome-icon>
                      <font-awesome-icon icon="plus-circle" @click="addIllicitRule()" v-show="k === vacationRental.illicitConductRules.length-1" style="color: #378220"></font-awesome-icon>
                    </span>
                    </div>
                  </div>
                </div>
                <div class="modal-footer"></div>
              </div>
            </div>
          </div>
        </div>
        <div class="row main pt-3">
          <div class="col-4"></div>
          <div class="col-6">
            <button type="button" @click="validateInputs" class="btn">Add vacation rental</button>
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
        address: {
          street: null,
          number: null,
          city: null,
          country: null,
          latitude: null,
          longitude: null
        },
        description: null,
        photos: [],
        capacity: null,
        price: null,
        cancellationFee: {
            feeType: null,
            value: null
        },
        additionalServices: [
          {
            name: null,
            price: null
          }
        ],
        allowedConductRules: [
          {
            rule: null,
            type: 'Do'
          }
        ],
        illicitConductRules: [
          {
            rule: null,
            type: 'DoNot'
          }
        ]
      }
    }
  },
  methods: {
    addService() {
      this.vacationRental.additionalServices.push({ name: null, price: null });
    },
    removeService(index) {
      this.vacationRental.additionalServices.splice(index, 1);
    },
    addAllowedRule() {
      this.vacationRental.allowedConductRules.push({ rule: null, type: 'Do' });
    },
    removeAllowedRule(index) {
      this.vacationRental.allowedConductRules.splice(index, 1);
    },
    addIllicitRule() {
      this.vacationRental.illicitConductRules.push({ rule: null, type: 'Do' });
    },
    removeIllicitRule(index) {
      this.vacationRental.illicitConductRules.splice(index, 1);
    },
    validateInputs() {
      this.validateAddress();
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
              console.log("NEMA ADRESE!")
              this.isAddressValid = false;
            }
            else {
              const location = responseView[0].Result[0].Location.DisplayPosition;
              const address = responseView[0].Result[0].Location.Address;
              this.vacationRental.address.city = address.City;
              this.vacationRental.address.country = address.AdditionalData[0].value;
              this.vacationRental.address.street = address.Street;
              this.vacationRental.address.longitude = location.Longitude;
              this.vacationRental.address.latitude = location.Latitude;
              console.log(this.vacationRental.address);
              this.isAddressValid = true;
            }
          })
          .catch(error => {
            console.log(error);
            this.isAddressValid = false;
          });
    }
  }
}
</script>

<style scoped>
input[type='text'], input[type='number'] {
  width: 100%;
  font-weight: 100;
}

input::placeholder {
  color: grey;
  font-weight: 100;
}
</style>
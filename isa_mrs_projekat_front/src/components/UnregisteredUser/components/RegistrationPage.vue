<template>
  <div class="container-fluid px-4 py-3 rounded" spellcheck="false" >
    <div class="row main">
      <div class="col main pt-3">
        <div class="row">
          <div class="col-4 main"></div>
          <div class="col-6 main">
            <div class="row mb-3">
              <p class="h6 ps-0 pb-0"><i>Type of user:</i></p>
              <select class="form-control" v-model="user.userType">
                <option value="Client" selected="selected">Client</option>
                <option value="VacationRentalOwner">Vacation Rental Owner</option>
                <option value="BoatOwner">Boat Owner</option>
                <option value="FishingInstructor">Instructor</option>
                <option v-if="isAdmin" value="Admin">Admin</option>
              </select>
            </div>
            <div class="row mb-3" v-bind:style="{color: isNamePresent ? '#3f5b25':'red'}">
              <p class="h6 ps-0 pb-0"><i>Name:</i></p>
              <input class="ps-1 h5" type="text" id="name" v-model="user.name" placeholder="E.g. Tamara">
            </div>
            <div class="row mb-3" v-bind:style="{color: isSurnamePresent ? '#3f5b25':'red'}">
              <p class="h6 ps-0 pb-0"><i>Surname:</i></p>
              <input class="ps-1 h5" type="text" id="surname" v-model="user.surname" placeholder="E.g. Volas">
            </div>
            <div class="row mb-3" v-bind:style="{color: isEmailPresent ? '#3f5b25':'red'}">
              <p class="h6 ps-0 pb-0"><i>E-mail:</i></p>
              <input class="ps-1 h5" type="email" id="email" v-model="user.email" placeholder="E.g. john@gmail.com">
            </div>
            <div class="row mb-3">
              <p class="h6 ps-0 pb-0" v-bind:style="{color: isPhoneValid || isPhoneValid ===null?'#3f5b25':'red'}">
                <i>Phone:</i>
              </p>
              <vue-tel-input v-model="phoneInput" mode="international" defaultCountry="RS" :preferredCountries=preferredCountries :onlyCountries=onlyCountries @input="telephone"></vue-tel-input>
            </div>
            <div v-if="!isAdmin" class="row">
              <p class="h6 ps-0 pb-0"><i>Profile pic <small>(optional)</small>:</i></p>
              <input class="p-0 h5" type="file" name="profilePic" @change="onFileSelected" accept="image/jpeg"/>
              <small class="p-0" style="color: grey;">Acceptable: jpg</small>
            </div>
          </div>
          <div class="col-2 main"></div>
        </div>
      </div>
      <div class="col main pt-3">
        <div class="row main">
          <div class="col-2 main"></div>
          <div class="col-6 main" v-bind:style="{color:!(isAddressValid || isAddressValid===null)?'red':'#3f5b25'}">
            <div class="row">
              <p class="h1 p-0"><font-awesome-icon icon="house"></font-awesome-icon> Address</p>
            </div>
            <div class="row mb-3">
              <p class="h6 ps-0 pb-0"><i>Country:</i></p>
              <input v-model="user.address.country" class="ps-1 h5" type="text" id="country" placeholder="Country">
            </div>
            <div class="row mb-3">
              <p class="h6 ps-0 pb-0"><i>City:</i></p>
              <input v-model="user.address.city" class="ps-1 h5" type="text" id="city" placeholder="City">
            </div>
            <div class="row mb-3">
              <p class="h6 ps-0 pb-0"><i>Street:</i></p>
              <input v-model="user.address.street" class="ps-1 h5" type="text" id="street" placeholder="Street">
            </div>
            <div class="row mb-3">
              <p class="h6 ps-0 pb-0"><i>Number:</i></p>
              <input v-model="user.address.number" class="ps-1 h5" type="text" id="number" placeholder="House Number">
            </div>
            <div v-if="isRentalObjectOwner" class="row">
              <textarea class="h6" v-model="user.reason" maxlength="300" rows="4"
                        placeholder="Why would you like to make an account?"></textarea>
            </div>
          </div>
          <div class="col-4 main">
          </div>
        </div>
      </div>
    </div>
    <div class="row main pt-3">
      <div class="col-4 main"></div>
      <div class="col-4 main px-5">
        <div class="row">
          <button type="button" @click="validateInputs" class="btn">Sign Up</button>
        </div>
      </div>
      <div class="col-4 main"></div>
    </div>
  </div>
</template>

<script>
import {FontAwesomeIcon} from "@fortawesome/vue-fontawesome";
import { library } from "@fortawesome/fontawesome-svg-core";
import { faHouse } from "@fortawesome/free-solid-svg-icons";
import { VueTelInput }  from 'vue3-tel-input';
import 'vue3-tel-input/dist/vue3-tel-input.css'
import store from "@/store";
import axios from "axios";

library.add(faHouse)

export default {
  name: "RegistrationPage",
  components: {
    VueTelInput,
    FontAwesomeIcon
  },
  data() {
    return {
      preferredCountries: ['RS', 'BA', 'ME', 'HR'],
      onlyCountries: ['RS', 'AL', 'BG', 'BA', 'HR', 'ME', 'SI', 'RO', 'MK'],
      phoneInput: null,
      user: {
        userType: "Client",
        name: null,
        surname: null,
        phoneNumber: null,
        email: null,
        photo: null,
        reason: null,
        address: {
          street: null,
          number: null,
          city: null,
          country: null,
          latitude: null,
          longitude: null
        }
      },
      isAddressValid: null,
      isPhoneValid: null,
      isNamePresent: null,
      isSurnamePresent: null,
      isEmailPresent: null,
      isReasonPresent: null,
      allInputsPresent: null,
    }
  },
  methods: {
    validateInputs() {
      this.validateAddress();
      this.validatePhone();
      this.isInputPresent();
      if(this.allInputsPresent && this.isAddressValid && this.isPhoneValid) this.submit();
    },
    submit() {
      axios.post("/Requests/signUp", null, {
        headers: {
          Authorization: "Bearer " + this.accessToken,
        },
        params: {
          userType: this.user.userType,
          name: this.user.name,
          surname: this.user.surname,
          phoneNumber: this.user.phoneNumber,
          email: this.user.email,
          photo: this.user.photo,
          reason: this.user.reason,
          address: {
            country: this.user.address.country,
            city: this.user.address.city,
            street: this.user.address.street,
            number: this.user.address.number,
            latitude: this.user.address.latitude,
            longitude: this.user.address.longitude
          }
        }
      })
      .then(() => {
        this.$notify( {
          title: "Sign up",
          text: "Request successfully sent.",
          position: "bottom right",
          type: "success"
        });
      })
    },
    isInputPresent() {
      this.isNamePresent = Boolean(this.user.name);
      this.isSurnamePresent = Boolean(this.user.surname);
      this.isEmailPresent = Boolean(this.user.email);
      this.isReasonPresent = !(!this.user.reason && this.user.userType !== "Client" && this.user.userType !== "Admin");
      this.allInputsPresent = !!(this.isNamePresent && this.isSurnamePresent && this.isEmailPresent && this.isReasonPresent);
    },
    validatePhone() {
      this.isPhoneValid = this.user.phoneNumber !== null;
    },
    validateAddress() {
      const apiKey = 'VrDrl5BjEA0Whvb-chHbFz96HV4qlCXB-yoiTRRLKno';
      const url = 'https://geocoder.ls.hereapi.com/6.2/geocode.json' +
          '?apiKey=' + apiKey +
          '&housenumber=' + this.user.address.number +
          '&street=' + this.user.address.street +
          '&city=' + this.user.address.city +
          '&country=' + this.user.address.country;
      fetch(url)
      .then(response => response.json())
      .then(data => {
        const responseView = data.Response.View;
        if (responseView.length === 0) {
          this.isAddressValid = false;
        }
        else {
          const location = responseView[0].Result[0].Location.DisplayPosition;
          const address = responseView[0].Result[0].Location.Address;
          this.user.address.city = address.City;
          this.user.address.country = address.AdditionalData[0].value;
          this.user.address.street = address.Street;
          this.user.address.longitude = location.Longitude;
          this.user.address.latitude = location.Latitude;
          this.isAddressValid = true;
        }
      })
      .catch(() => {
        this.isAddressValid = false;
      });
    },
    onFileSelected(event) {
      this.user.photo = event.target.files[0];
    },
    telephone(phone, phoneObject) {
      if (phoneObject?.valid) {
        this.user.phoneNumber = phoneObject.number;
      } else if (phoneObject) {
        this.user.phoneNumber = null;
      }
    }
  },
  computed: {
    isAdmin() {
      return store.state.user === "SuperAdmin";
    },
    isRentalObjectOwner() {
      return (this.user.userType !== "Client" && this.user.userType !== "Admin");
    }
  }
}
</script>

<style scoped>
  .container-fluid {
    outline: solid 2px #3f5b25;
    margin-top: 10px;
    color: #3f5b25;
  }

  textarea {
    padding: 5px 10px;
    resize: none;
    background-color: #378220;
    color: #f7f7f2;
    outline: none;
    width: 100%;
    border-radius: 5px;
    text-align: justify;
    font-weight: 100;
  }

  textarea::placeholder{
    color: white;
    font-style: italic;
  }

  input[type='text'], input[type='email'] {
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
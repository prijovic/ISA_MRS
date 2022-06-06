<template>
  <div class="row" v-if="this.user">
    <div class="col-2"></div>
    <div class="col-8 pt-5 mb-5">
      <div class="container px-4 py-3 rounded form" spellcheck="false" >
        <div class="container-fluid">
          <h3>Edit personal data</h3>
          <div class="row main justify-content-center">
            <div class="row main">
              <div class="col main">
                <div class="row">
                  <div class="col-4"></div>
                  <div class="col-6">
                    <div class="row">
                      <label for="name">Name</label>
                      <input class="form-control" type="text" id="name" v-model="user.name" placeholder="Name" @input="nameIsEntered=true">
                      <p v-if='!nameIsEntered'>'Name' is a mandatory field.</p>
                    </div>
                    <div class="row">
                      <label for="surname">Surname</label>
                      <input class="form-control" type="text" id="surname" v-model="user.surname" placeholder="Surname" @input="surnameIsEntered=true">
                      <p v-if='!surnameIsEntered'>'Surname' is a mandatory field.</p>
                    </div>
                    <div class="row">
                      <label for="email">Email</label>
                      <input class="form-control" disabled type="email" id="email" v-model="user.email" placeholder="Email" @input="emailIsEntered=true">
                      <p v-if='!emailIsEntered'>'Email' is a mandatory field.</p>
                    </div>
                    <div class="row">
                      <label for="phone">Phone</label>
                      <vue-tel-input v-model="phoneInput" id="phone" mode="international" defaultCountry="RS" :onlyCountries=onlyCountries @input="onTelephoneInput"></vue-tel-input>
                      <p v-if='!phoneIsValid'>Invalid phone number.</p>
                    </div>
                  </div>
                  <div class="col-2"></div>
                </div>
              </div>
              <div class="col main">
                <div class="row">
                  <div class="col-2"></div>
                  <div class="col-6">
                    <div class="row">
                      <label for="country">Country</label>
                      <input v-model="user.address.country" class="form-control" type="text" id="country" placeholder="Country" @input="countryIsEntered=true">
                      <p v-if='!countryIsEntered'>'Country' is a mandatory field.</p>
                      <p v-if='!addressIsValid'>Invalid address data.</p>
                    </div>
                    <div class="row">
                      <label for="city">City</label>
                      <input v-model="user.address.city" class="form-control" type="text" id="city" placeholder="City" @input="cityIsEntered=true">
                      <p v-if='!cityIsEntered'>'City' is a mandatory field.</p>
                      <p v-if='!addressIsValid'>Invalid address data.</p>
                    </div>
                    <div class="row">
                      <label for="street">Street</label>
                      <input v-model="user.address.street" class="form-control" type="text" id="street" placeholder="Street" @input="streetIsEntered=true">
                      <p v-if='!streetIsEntered'>'Street' is a mandatory field.</p>
                      <p v-if='!addressIsValid'>Invalid address data.</p>
                    </div>
                    <div class="row">
                      <label for="number">Number</label>
                      <input v-model="user.address.number" class="form-control"  type="text" id="number" placeholder="House Number" @input="numberIsEntered=true">
                      <p v-if='!numberIsEntered'>'Number' is a mandatory field.</p>
                      <p v-if='!addressIsValid'>Invalid address data.</p>
                    </div>
                  </div>
                  <div class="col-4">
                  </div>
                </div>
              </div>
            </div>
          </div>
          <div class="d-flex pt-3 justify-content-center">
            <router-link to="/#" class="btn btn-red mt-3 me-1">Cancel</router-link>
            <button type="button" class="btn mt-3" @click="submit">Submit</button>
          </div>
        </div>
      </div>
    </div>
    <div class="col-2"></div>
  </div>
</template>

<script>

import { VueTelInput }  from 'vue3-tel-input';
import 'vue3-tel-input/dist/vue3-tel-input.css';
import axios from "axios";
import {toggleProcessing} from "@/components/state";

export default {
  name: "EditPersonalData",
  components: {VueTelInput},
  data(){
    return {
      onlyCountries: ['RS', 'BA', 'HR', 'ME', 'SI', 'MK'],
      phoneInput: null,
      user: {
        name: null,
        surname: null,
        phoneNumber: null,
        email: null,
        address: {
          street: null,
          number: null,
          city: null,
          country: null,
          latitude: null,
          longitude: null
        }
      },
      nameIsEntered: true,
      surnameIsEntered: true,
      emailIsEntered: true,
      countryIsEntered: true,
      cityIsEntered: true,
      streetIsEntered: true,
      numberIsEntered: true,
      phoneIsEntered: true,
      addressIsValid: true,
      phoneIsValid: true,
    }
  },
  computed: {
    isNameEntered() {
      return Boolean(this.user.name);
    },
    isSurnameEntered() {
      return Boolean(this.user.surname);
    },
    isEmailEntered() {
      return Boolean(this.user.email);
    },
    isCountryEntered() {
      return Boolean(this.user.address.country);
    },
    isCityEntered() {
      return Boolean(this.user.address.city);
    },
    isStreetEntered() {
      return Boolean(this.user.address.street);
    },
    isNumberEntered() {
      return Boolean(this.user.address.number);
    }
  },
  methods: {
    submit(){
      if (this.isDataEntered()) {
        this.isDataCorrect();
        this.editData();
      }
    },
    editData() {
      axios.put("/Users/editUser", this.user, {
        headers: {
          Authorization: "Bearer " + this.$store.getters.access_token,
        }
      })
          .then(() => {
            this.$notify({
              title: "Successful edit",
              text: "You have successfully edited your personal data.",
              position: "bottom right",
              type: "success"
            });
            toggleProcessing();
            this.$router.push("/");
          })
          .catch(error => {
            if (!error.response) {
              this.$notify({
                title: "Server error",
                text: "Server is currently off. Please try again later...",
                type: "error"
              });
              toggleProcessing();
            } else if (error.response.status === 500) {
              this.$notify({
                title: "Internal Server Error",
                text: "Something went wrong on the server! Please try again later...",
                position: "bottom right",
                type: "error"
              });
              toggleProcessing();
            }
          })
    },
    isDataEntered() {
      if (!this.isNameEntered) {
        this.nameIsEntered = false;
        return false;
      }
      if (!this.isSurnameEntered) {
        this.surnameIsEntered = false;
        return false;
      }
      if (!this.isEmailEntered) {
        this.emailIsEntered = false;
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
    async isDataCorrect() {
      if (!this.isPhoneValid()){
        this.phoneIsValid = false;
        return false;
      } else {
        this.validateAddress();
      }
    },
    onTelephoneInput(phone, phoneObject) {
      this.phoneIsEntered = true;
      if (phoneObject?.valid) {
        this.phoneIsValid = true;
        this.user.phoneNumber = phoneObject.number;
      } else if (phoneObject) {
        this.user.phoneNumber = null;
      }
    },
    isPhoneValid() {
      return Boolean(this.user.phoneNumber);
    },
    validateAddress() {
      toggleProcessing();
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
              this.addressIsValid = false;
            }
            else {
              const location = responseView[0].Result[0].Location.DisplayPosition;
              const address = responseView[0].Result[0].Location.Address;
              this.user.address.city = this.transliterate(address.City);
              this.user.address.country = this.transliterate(address.AdditionalData[0].value);
              this.user.address.street = this.transliterate(address.Street);
              this.user.address.longitude = location.Longitude;
              this.user.address.latitude = location.Latitude;
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
  },
  mounted(){
    toggleProcessing();
    axios.get("/Users/getUser", {
      headers: {
        Authorization: "Bearer " + this.$store.getters.access_token,
      }
    })
        .then(response => {
          this.user = response.data;
          console.log(response.data);
          toggleProcessing();
        })
        .catch(() =>{
          this.$notify({
            title: "Server error",
            text: "Server is currently off. Please try again later...",
            type: "error"
          });
          toggleProcessing();
        })
  }
}
</script>

<style scoped>
textarea {
  resize: none;
  width: 100%;
  text-align: justify;
}

input[type='text'], input[type='email'] {
  width: 100%;
}

input::placeholder {
  color: grey;
}

h3 {
  text-align: center;
}
p {
  color: #e23c52;
}
</style>
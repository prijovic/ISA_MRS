<template>
  <div class="row">
    <div class="col-2"></div>
    <div class="col-8 pt-5 mb-5">
      <div class="container px-4 py-3 rounded form" spellcheck="false" >
        <div class="container-fluid">
          <h3 v-if="!isAdmin">Sign Up</h3>
          <h3 v-else>New User</h3>
          <div class="row main justify-content-center">
            <div class="row main">
              <div class="col main">
                <div class="row">
                  <div class="col-4"></div>
                  <div class="col-6">
                    <div class="row">
                      <label for="userType">User type</label>
                      <select class="form-control" v-model="user.userType" id="userType">
                        <option value="Client" selected="selected">Client</option>
                        <option value="VacationRentalOwner">House Owner</option>
                        <option value="BoatOwner">Boat Owner</option>
                        <option value="Instructor">Fishing Instructor</option>
                        <option v-if="isAdmin" value="Admin">Admin</option>
                      </select>
                    </div>
                    <div class="row">
                      <label for="name">Name</label>
                      <input class="form-control" type="text" id="name" v-model="user.name" placeholder="E.g. John" @input="nameIsEntered=true">
                      <p v-if='!nameIsEntered'>'Name' is a mandatory field.</p>
                    </div>
                    <div class="row">
                      <label for="surname">Surname</label>
                      <input class="form-control" type="text" id="surname" v-model="user.surname" placeholder="E.g. Doe" @input="surnameIsEntered=true">
                      <p v-if='!surnameIsEntered'>'Surname' is a mandatory field.</p>
                    </div>
                    <div class="row">
                      <label for="email">Email</label>
                      <input class="form-control" type="email" id="email" v-model="user.email" placeholder="E.g. john@email.com" @input="emailChanged">
                      <p v-if='!emailIsEntered'>'Email' is a mandatory field.</p>
                      <p v-else-if='!emailValidation'>Invalid email format.</p>
                      <p v-else-if='!emailIsUnique'>Email is already taken.</p>
                    </div>
                    <div v-if="!isAdmin" class="row">
                      <label for="inputPassword2">Password</label>
                      <input type="password" id="inputPassword2" v-model="user.password" class="form-control col-sm-auto col-lg-4" aria-describedby="passwordHelpBlock" placeholder="New password" @input="passwordISEntered=true">
                      <p v-if="!passwordISEntered">'Password' is a mandatory field.</p>
                      <p v-else-if='!passwordValidation.valid'>{{ passwordValidation.errors[0] }}</p>
                      <small id="passwordHelpBlock" class="form-text text-muted">
                        Your password must be 8-20 characters long, contain letters and numbers, and must not contain spaces, special characters, or emoji.
                      </small>
                    </div>
                    <div v-if="!isAdmin" class="row">
                      <label for="inputPassword3">Confirm Password</label>
                      <input type="password" id="inputPassword3" v-model.lazy="checkPassword" class="form-control col-sm-auto col-lg-4" aria-describedby="passwordHelpBlock" placeholder="Confirm password" @input="confirmationPasswordIsEntered=true">
                      <p v-if="!confirmationPasswordIsEntered">'Confirmation Password' is a mandatory field.</p>
                      <p  v-else-if='notSamePasswords'>Passwords don't match.</p>
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
                    <div class="row">
                      <label for="phone">Phone</label>
                      <vue-tel-input v-model="phoneInput" id="phone" mode="international" defaultCountry="RS" :onlyCountries=onlyCountries @input="onTelephoneInput"></vue-tel-input>
                      <p v-if='!phoneIsValid'>Invalid phone number.</p>
                    </div>
                    <div class="row" v-if="isRentalObjectOwner && !isAdmin">
                      <label for="motivationLetter">Motivation Letter</label>
                      <textarea v-model="user.reason" class="form-control" rows="10" maxlength="300" placeholder="Please describe why you want to join our community in 50-500 characters." id="motivationLetter" @input="reasonIsEntered=true"></textarea>
                      <p v-if='!reasonIsEntered'>'Motivation Letter' is a mandatory field.</p>
                      <p v-if='!reasonIsValid'>Minimum characters is 50.</p>
                    </div>
                  </div>
                  <div class="col-4">
                  </div>
                </div>
              </div>
            </div>
          </div>
          <div class="d-flex pt-3 justify-content-center">
            <router-link v-if="isAdmin" to="/admin/users" class="btn btn-red mt-3 me-1">Cancel</router-link>
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
import store from "@/store";
import axios from "axios";
import {toggleProcessing} from "@/components/state";

export default {
  name: "RegistrationPage",
  components: {VueTelInput},
  data() {
    return {
      rules: [
        { message:'One lowercase letter required.', regex:/[a-z]+/ },
        { message:"One uppercase letter required.",  regex:/[A-Z]+/ },
        { message:"8-20 characters required.", regex:/.{8,20}/ },
        { message:"One number required.", regex:/[0-9]+/ }
      ],
      checkPassword:'',
      onlyCountries: ['RS', 'BA', 'HR', 'ME', 'SI', 'MK'],
      phoneInput: null,
      user: {
        userType: "Client",
        name: null,
        surname: null,
        phoneNumber: null,
        email: null,
        password: '',
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
      nameIsEntered: true,
      surnameIsEntered: true,
      emailIsEntered: true,
      countryIsEntered: true,
      cityIsEntered: true,
      streetIsEntered: true,
      numberIsEntered: true,
      phoneIsEntered: true,
      passwordISEntered: true,
      confirmationPasswordIsEntered: true,
      reasonIsEntered: true,
      addressIsValid: true,
      phoneIsValid: true,
      reasonIsValid: true,
      emailIsUnique: true
    }
  },
  computed: {
    accessToken() {
      return store.state.access_token;
    },
    isAdmin() {
      return store.state.isSuperAdmin;
    },
    isRentalObjectOwner() {
      return (this.user.userType !== "Client" && this.user.userType !== "Admin" && this.user.userType !== "SuperAdmin");
    },
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
    },
    isPasswordEntered() {
      return Boolean(this.user.password);
    },
    isConfirmationPasswordEntered() {
      return Boolean(this.checkPassword);
    },
    isReasonEntered() {
      return Boolean(this.user.reason);
    },
    notSamePasswords () {
      if (this.isPasswordEntered && this.isConfirmationPasswordEntered) {
        return (this.user.password !== this.checkPassword)
      } else {
        return false
      }
    },
    passwordValidation () {
      let errors = []
      if (this.user.password !== ''){
        for (let condition of this.rules) {
          if (!condition.regex.test(this.user.password)) {
            errors.push(condition.message)
          }
        }
        if (errors.length === 0) {
          return { valid:true, errors }
        } else {
          return { valid:false, errors }
        }
      }
      else {
        return { valid:true, errors }
      }
    },
    emailValidation() {
      if (this.isEmailEntered) {
        let emailRule = {regex:/^[\w-.]+@([\w-]+\.)+[\w-]{2,4}/};
        return emailRule.regex.test(this.user.email);
      }
      return true;
    }
  },
  methods: {
    emailChanged() {
      this.emailIsEntered = true;
      this.emailIsUnique = true;
    },
    submit() {
      if (this.passwordValidation.valid && !this.notSamePasswords) {
        if (this.isDataEntered()) {
          this.isDataCorrect();
        }
      }
    },
    makeRequest() {
      if (this.isAdmin) {
        axios.post("/Users/addUser", this.user, {
          headers: {
            Authorization: "Bearer " + this.accessToken,
          }
        })
        .then(() => {
          this.$notify( {
            title: "Successful adding",
            text: "You have successfully added a new user. Verification email has been sent to " + this.user.email + ".",
            position: "bottom right",
            type: "success"
          });
          toggleProcessing();
          this.$router.push("/admin/users");
        })
        .catch(error => {
          if (!error.response) {
            this.$notify({
              title: "Server error",
              text: "Server is currently off. Please try again later...",
              type: "error"
            });
            toggleProcessing();
          } else if (error.response.status === 422) {
            toggleProcessing();
            this.emailIsUnique = false;
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
      } else {
        axios.post("/Requests/signUp", this.user, {
          headers: {
            Authorization: "Bearer " + this.accessToken,
          }
        })
        .then(() => {
          let message = "Successful Sign Up";
          if(this.user.userType === "Client") message += "Verification email has been sent to " + this.user.email + ". Please go and check your mail.";
          else message += "Your request is pending. Keep checking your email for a response."
          this.$notify( {
            title: "Sign up",
            text: message,
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
          } else if (error.response.status === 422) {
            toggleProcessing();
            this.emailIsUnique = false;
          } else if (error.response.status === 400) {
            this.$notify({
              title: "Invalid Request Status",
              text: "Bad registration request.",
              position: "bottom right",
              type: "warn"
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
      }

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
      if (!this.isPasswordEntered && !this.isAdmin) {
        this.passwordISEntered = false;
        return false;
      }
      if (!this.isConfirmationPasswordEntered  && !this.isAdmin) {
        this.confirmationPasswordIsEntered = false;
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
      if (this.isRentalObjectOwner && !this.isReasonEntered && !this.isAdmin) {
        this.reasonIsEntered = false;
        return false;
      }
      return true;
    },
    async isDataCorrect() {
      if (!this.isPhoneValid()){
        this.phoneIsValid = false;
        return false;
      }
      else if (this.isRentalObjectOwner && this.isReasonValid() && !this.isAdmin) {
        this.reasonIsValid = false;
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
    isReasonValid() {
      return this.user.reason.length >= 50;
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
              this.makeRequest();
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
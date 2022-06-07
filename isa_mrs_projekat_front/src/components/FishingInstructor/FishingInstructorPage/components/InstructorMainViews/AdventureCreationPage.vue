<template>
  <div class="row">
    <div class="col-2"></div>
    <div class="col-8 pt-5 mb-5">
      <div class="container px-4 py-3 rounded form" spellcheck="false" >
        <div class="container-fluid">
          <h3>New Adventure</h3>
          <div class="row main justify-content-center">
            <div class="row main">
              <div class="col-2"></div>
              <div class="col-8">

                <div class="row justify-content-center">
                  <div class="row justify-content-center">
                    <label for="name">Name</label>
                  </div>
                  <input class="form-control" v-model="adventure.name" type="text" id="name" placeholder="E.g. Adventure" @input="nameIsEntered=true">
                  <p v-if='!nameIsEntered'>'Name' is a mandatory field.</p>
                </div>
                <div class="row justify-content-center">
                  <div class="row justify-content-center">
                    <label for="description">Description</label>
                  </div>
                  <textarea  class="form-control" v-model="adventure.description" rows="10" maxlength="500" placeholder="Please describe why you want to join our community in 50-500 characters." id="description" @input="descriptionIsEntered=true"></textarea>
                  <p v-if='!descriptionIsEntered'>'Description' is a mandatory field.</p>
                </div>
                <div class="row justify-content-center">
                  <div class="row justify-content-center">
                    <label for="description">Duration</label>
                  </div>
                  <div class="p-0" style="display: flex; max-width: 400px">
                    <input v-model="hours" class="form-control" type="number" style="width: 60px" min="0" @input="durationIsEntered=true"><div style="align-self: flex-end;font-size: 22px">h</div>
                    <input v-model="minutes" class="form-control ms-2" type="number" style="width: 60px" min="0" max="59" @input="durationIsEntered=true"><div style="align-self: flex-end;font-size: 22px">min</div>
                  </div>
                  <p v-if='!durationIsEntered'>'Duration' is a mandatory field.</p>
                  <p v-if='null'>Please convert minutes to hours.</p>
                </div>

                <div class="row mt-4 justify-content-center">
                  <div class="row justify-content-center">
                    <label for="country">Country</label>
                  </div>
                  <input  v-model="adventure.address.country" class="form-control" type="text" id="country" placeholder="Country" @input="countryIsEntered=true">
                  <p v-if='!countryIsEntered'>'Country' is a mandatory field.</p>
                  <p v-if='!addressIsValid'>Invalid address data.</p>
                </div>
                <div class="row justify-content-center">
                  <div class="row justify-content-center">
                    <label for="city">City</label>
                  </div>
                  <input v-model="adventure.address.city" class="form-control" type="text" id="city" placeholder="City" @input="cityIsEntered=true">
                  <p v-if='!cityIsEntered'>'City' is a mandatory field.</p>
                  <p v-if='!addressIsValid'>Invalid address data.</p>
                </div>
                <div class="row justify-content-center">
                  <div class="row justify-content-center">
                    <label for="street">Street</label>
                  </div>
                  <input v-model="adventure.address.street" class="form-control" type="text" id="street" placeholder="Street" @input="streetIsEntered=true">
                  <p v-if='!streetIsEntered'>'Street' is a mandatory field.</p>
                  <p v-if='!addressIsValid'>Invalid address data.</p>
                </div>
                <div class="row justify-content-center">
                  <div class="row justify-content-center">
                    <label for="number">Number</label>
                  </div>
                  <input v-model="adventure.address.number" class="form-control"  type="text" id="number" placeholder="House Number" @input="numberIsEntered=true">
                  <p v-if='!numberIsEntered'>'Number' is a mandatory field.</p>
                  <p v-if='!addressIsValid'>Invalid address data.</p>
                </div>

                <div class="row mt-4 justify-content-center">
                  <div class="row justify-content-center">
                    <label for="price">Price</label>
                  </div>
                  <input v-model="adventure.price" type="number" step="any" min="0"  id="price" class="form-control" placeholder="Price in $" @input="priceIsEntered=true">
                  <p v-if='!priceIsEntered'>'Price' is a mandatory field.</p>
                </div>
                <div class="row justify-content-center">
                  <div class="row justify-content-center">
                    <label for="capacity">Capacity</label>
                  </div>
                  <input v-model="adventure.capacity" type="number" step="any" min="0"  id="capacity" class="form-control" placeholder="Max people" @input="capacityIsEntered=true">
                  <p v-if='!capacityIsEntered'>'Capacity' is a mandatory field.</p>
                </div>
                <div class="row justify-content-center">
                  <div class="row justify-content-center">
                    <label for="cancellation">Cancellation fee</label>
                  </div>
                  <input v-model="adventure.cancellationFee.value" type="number" step="any" min="0" max="100"  id="cancellation" class="form-control" placeholder="Cancellation fee in %" @input="cancellationFeeIsEntered=true">
                  <p v-if='!cancellationFeeIsEntered'>'Cancellation fee' is a mandatory field.</p>
                </div>

                <div class="row mt-4 justify-content-center">
                  <div class="row justify-content-center">
                    <label for="equipment">Equipment</label>
                  </div>
                  <input class="form-control" v-model="equipmentPiece.name" type="text" id="equipment" placeholder="E.g. Fishing rod" @keyup.enter="addEquipment">
                  <div class="row justify-content-center">
                    <small class="form-text text-muted">
                      Press Enter to add equipment
                    </small>
                  </div>
                  <div class="row tag-container">
                    <p v-if='equipmentAlreadyExists'>Equipment already exists.</p>
                    <div class="equipment-tag rounded-pill mt-1 ms-1 p-1" v-for="(pieceOfEquipment, index) in adventure.adventureEquipment" :key="index">
                      {{pieceOfEquipment.name}}
                      <button class="tag-button text-center" @click="removeEquipment(pieceOfEquipment)"><font-awesome-icon icon="x" style="width: 9px; height: 9px"></font-awesome-icon></button>
                    </div>
                  </div>
                </div>

                <div class="row mt-4 justify-content-center">
                  <div class="justify-content-center p-0" style="max-width: 400px;">
                    <div class="d-flex d-inline-flex justify-content-between p-0 w-100">
                      <label for="additionalServices" class="ms-3">Additional Services</label>
                      <div class="dropdown">
                        <button class="btn dropdown" type="button" id="servicesDropdown" data-bs-toggle="dropdown" aria-expanded="false">
                          <font-awesome-icon icon="plus"></font-awesome-icon>
                        </button>
                        <form class="dropdown-menu dropdown-menu-end form p-4 m-0" aria-labelledby="servicesDropdown">
                          <h4>New Service</h4>
                          <label for="service" class="ms-2">Name</label>
                          <input v-model="additionalService" class="form-control m-0" type="text" placeholder="E.g. Spa" id="service">
                          <label for="servicePrice" class="ms-2">Price</label>
                          <input v-model="additionalServicePrice" type="number" step="any" min="0"  id="servicePrice" class="form-control" placeholder="Price in $">
                          <div class="text-center">
                            <button class="btn mt-2" type="button" @click.prevent="addService">Add</button>
                          </div>
                        </form>
                      </div>
                    </div>
                    <div class="row tag-container" id="additionalServices">
                      <p v-if='serviceAlreadyExists'>Additional service already exists.</p>
                      <div class="equipment-tag rounded-pill mt-1 ms-1 p-1" v-for="(additionalService, index) in adventure.additionalServices" :key="index">
                        {{additionalService.name + " " + (additionalService.price===null?"Free":additionalService.price + "$")}}
                        <button class="tag-button text-center" @click="removeService(additionalService)"><font-awesome-icon icon="x" style="width: 9px; height: 9px"></font-awesome-icon></button>
                      </div>
                    </div>
                  </div>
                </div>

                <div class="row mt-4 justify-content-center">
                  <div class="justify-content-center p-0" style="max-width: 400px;">
                    <div class="d-flex d-inline-flex justify-content-between p-0 w-100">
                      <label for="rulesTable" class="ms-3">Rules of Conduct</label>
                      <div class="dropdown">
                        <button class="btn dropdown" type="button" id="rulesDropdown" data-bs-toggle="dropdown" aria-expanded="false">
                          <font-awesome-icon icon="plus"></font-awesome-icon>
                        </button>
                        <form class="dropdown-menu dropdown-menu-end form p-4 m-0" aria-labelledby="rulesDropdown">
                          <h4>New Rule</h4>
                          <label for="conductRule" class="ms-2">Rule</label>
                          <input v-model="ruleText" class="form-control m-0" type="text" id="conductRule">
                          <label for="ruleType" class="ms-2">Rule Type</label>
                          <select v-model="ruleType" class="form-control m-0" id="ruleType">
                            <option value="Do">Do</option>
                            <option value="DoNot" selected="selected">Don't</option>
                          </select>
                          <div class="text-center">
                            <button class="btn mt-2" type="button" @click.prevent="addRule">Add</button>
                          </div>
                        </form>
                      </div>
                    </div>
                    <div class=" row justify-content-center p-0">
                      <p v-if='ruleAlreadyExists'>Rule already exists.</p>
                      <table class="mt-1 tag-container" id="rulesTable" style="max-width: 400px">
                        <thead v-if="rulesOfConduct.length > 0" style="background-color: transparent">
                        <tr>
                          <th style="background-color: #008970; color: #f7f7f2; ; border-bottom: #008970 1px solid">Do</th>
                          <th style="background-color: #e23c52; color: #f7f7f2; border-left: #008970 1px solid; border-bottom: #e23c52 1px solid">Don't</th>
                        </tr>
                        </thead>
                        <tbody>
                        <tr v-for="(rule, index) in rulesOfConduct" :key="index" :class="index%2!==0?'odd':'even'">
                          <td style="border-right: #008970 1px solid;word-wrap: break-word;">
                            <div class="d-flex d-inline-flex justify-content-between p-0">
                              <div>{{rule.Do}}</div><button v-if="rule.Do" class="button" style="border: none; background-color: transparent; color: #e23c52" @click="removeRule(rule.Do)"><font-awesome-icon icon="minus"></font-awesome-icon></button>
                            </div>
                          </td>
                          <td style="word-wrap: break-word;">
                            <div class="d-flex d-inline-flex justify-content-between p-0">
                              <div>{{rule.DoNot}}</div><button v-if="rule.DoNot" class="button" style="border: none; background-color: transparent; color: #e23c52" @click="removeRule(rule.DoNot)"><font-awesome-icon icon="minus"></font-awesome-icon></button>                            </div>
                          </td>
                        </tr>
                        </tbody>
                      </table>
                    </div>
                  </div>
                </div>

                <div class="row justify-content-center mt-4">
                  <div class="row justify-content-center">
                    <label for="imageInput">Images</label>
                    <p v-if='!photoIsEntered'>One image is mandatory.</p>
                  </div>
                  <div class="row p-0 justify-content-center" id="imageInput" style="max-width: 400px;min-width: 120px">
                    <label class="imageUploadLabel text-center rounded m-1" style="background-color:white;display: table;width: 180px;height: 100px;border: 1px solid #008970;cursor: pointer">
                      <span style="display:table-cell; vertical-align: middle">
                        <font-awesome-icon class="rounded-circle" icon="plus-circle" style="color: #008970; background-color: transparent;height: 50px"></font-awesome-icon>
                        <input class="upload-button" multiple type="file" accept="image/jpeg" ref="photo" @change="addPhoto">
                      </span>
                    </label>
                    <div v-for="(url, index) in imageUrls" class="m-1 rounded" :key="index" style="width:180px;height:100px;border: 1px solid #008970;background-color: lightgray;position: relative;text-align: center">
                      <img :src="url" alt="" style="width:100%; height:100%; object-fit: contain;">
                      <button style="background-color: transparent;color: #e23c52;border:none;position: absolute;top: 0;right: 0;cursor: pointer" @click="removePhoto(index)"><font-awesome-icon icon="x"></font-awesome-icon></button>
                    </div>
                  </div>
                </div>

                <div class="row justify-content-center mt-4">
                  <label class="ms-3">Availability Period</label>
                  <availability-period-selector :rental="rentalPeriod"></availability-period-selector>
                </div>

              </div>
              <div class="col-2"></div>
            </div>
          </div>
          <div class="d-flex pt-3 justify-content-center">
            <router-link to="/fishingInstructor/adventures" class="btn btn-red mt-3 me-1">Cancel</router-link>
            <button type="button" class="btn mt-3" @click="submit">Submit</button>
          </div>
        </div>
      </div>
    </div>
    <div class="col-2"></div>
  </div>
</template>

<script>
import {FontAwesomeIcon} from "@fortawesome/vue-fontawesome";
import {library} from "@fortawesome/fontawesome-svg-core";
import {faMinus, faPlus, faPlusCircle, faX} from "@fortawesome/free-solid-svg-icons";
import axios from "axios";
import {toggleLoading, toggleProcessing} from "@/components/state";
import store from "@/store";
import AvailabilityPeriodSelector from "@/components/RentalObjectOwner/AvailabilityPeriodSelector";

library.add(faPlus, faMinus, faX, faPlusCircle);

export default {
  name: "AdventureCreationPage",
  components: {AvailabilityPeriodSelector, FontAwesomeIcon},
  data() {
    return {
      adventure: {
        id: null,
        name: null,
        description: null,
        capacity: null,
        price: null,
        duration: 0,
        additionalServices: [],
        conductRules: [],
        cancellationFee: {value: null},
        address: {
          country: null,
          city: null,
          street: null,
          number: null,
          longitude: null,
          latitude: null
        },
        adventureEquipment: [],
        availabilityPeriod: {
          initDate: null,
          termDate: null,
        }
      },
      photo: null,
      photos: [],
      imageUrls: [],
      equipmentPiece: {name:""},
      additionalService: "",
      additionalServicePrice: null,
      ruleType: "Do",
      ruleText: "",
      hours: 0,
      minutes: 0,
      ruleIsValid: true,
      ruleAlreadyExists: false,
      serviceAlreadyExists: false,
      equipmentAlreadyExists: false,
      nameIsEntered: true,
      durationIsEntered: true,
      descriptionIsEntered: true,
      countryIsEntered: true,
      cityIsEntered: true,
      streetIsEntered: true,
      numberIsEntered: true,
      priceIsEntered: true,
      capacityIsEntered: true,
      cancellationFeeIsEntered: true,
      photoIsEntered: true,
      addressIsValid: true
    }
  },
  mounted() {
    toggleLoading();
    if (this.$route.params.id !== undefined) {
      axios.get("/RentalObjects/getAdventureInstructor", {
        headers: {
          Authorization: "Bearer " + store.getters.access_token,
        },
        params: {
          id: this.$route.params.id
        }
      })
      .then((response) => {
        let adventure = response.data;
        this.adventure.id = adventure.id;
        this.adventure.name = adventure.name;
        this.adventure.description = adventure.description;
        this.hours = Math.trunc(adventure.duration);
        this.minutes = 60*(adventure.duration%1);
        this.adventure.address = adventure.address;
        this.adventure.price = adventure.price;
        this.adventure.capacity = adventure.capacity;
        this.adventure.adventureEquipment = adventure.adventureEquipment;
        this.adventure.additionalServices = adventure.additionalServices;
        this.adventure.conductRules = adventure.conductRules;
        adventure.photos.forEach(photo => {
          this.photos.push(photo.photo);
          this.loadImage(photo.photo, this.photos.indexOf(photo.photo));
        });
        console.log(adventure);
        if (adventure.availabilityPeriod !== null) {
          this.adventure.availabilityPeriod.initDate = adventure.availabilityPeriod.initDate;
          this.adventure.availabilityPeriod.termDate = adventure.availabilityPeriod.termDate;
        }
        console.log(this.adventure);
        toggleLoading();
      })
      .catch(() => {
        this.$notify({
          title: "Server error",
          text: "Server is currently off. Please try again later...",
          type: "error"
        });
        toggleLoading();
      })
    }
  },
  computed: {
    rentalPeriod() {
      if (this.adventure.availabilityPeriod.initDate !== null) {
        return {start: new Date(this.adventure.availabilityPeriod.initDate), end: new Date(this.adventure.availabilityPeriod.termDate)};
      } else {
        return null;
      }
    },
    rulesOfConduct() {
      let pairsOfRules = [];
      let positiveRules = this.positiveRules(this.adventure.conductRules);
      let negativeRules = this.negativeRules(this.adventure.conductRules);
      let size = Math.max(positiveRules.length, negativeRules.length);
      for (let i = 0; i < size; i++) {
        pairsOfRules.push({
          "Do": i + 1 <= positiveRules.length ? positiveRules[i] : "",
          "DoNot": i + 1 <= negativeRules.length ? negativeRules[i] : ""
        });
      }
      return pairsOfRules;
    },
    isNameEntered() {
      return Boolean(this.adventure.name);
    },
    isDescriptionEntered() {
      return Boolean(this.adventure.description);
    },
    isDurationEntered() {
      return this.adventure.duration > 0;
    },
    isCountryEntered() {
      return Boolean(this.adventure.address.country);
    },
    isCityEntered() {
      return Boolean(this.adventure.address.city);
    },
    isStreetEntered() {
      return Boolean(this.adventure.address.street);
    },
    isNumberEntered() {
      return Boolean(this.adventure.address.number);
    },
    isPriceEntered() {
      return Boolean(this.adventure.price);
    },
    isCapacityEntered() {
      return Boolean(this.adventure.capacity);
    },
    isCancellationFeeEntered() {
      return Boolean(this.adventure.cancellationFee.value);
    },
    isPhotoEntered() {
      return this.photos.length > 0;
    }
  },
  methods: {
    submit() {
      this.adventure.duration = this.minutes/60 + this.hours;
      if (this.isDataEntered()) {
        this.isDataCorrect();
      }
    },
    makeRequest() {
      axios.post("/RentalObjects/addAdventure", this.adventure, {
        headers: {
          Authorization: "Bearer " + this.$store.getters.access_token,
        }
      })
      .then((adventure) => {
        for (let i = 0; i < this.photos.length; i++) {
          let formData = new FormData();
          formData.append("file", this.photos[i]);
          axios.post("/Photos/upload", formData, {
            headers: {
              Authorization: "Bearer " + this.$store.getters.access_token,
              "Content-type": "multipart/form-data"
            },
          })
          .then((response) => {
            let body = {id: adventure.data.id, photos: [response.data]}
            axios.post("/RentalObjects/connectPhotosToRental", body,{
              headers: {
                Authorization: "Bearer " + this.$store.getters.access_token,
              },
            }).then(() =>{
              this.$notify( {
                title: "Successful adding",
                text: "You have successfully added a new adventure.",
                position: "bottom right",
                type: "success"
              })
            })
          })
        }
      })
      .catch(error => {
        if (!error.response) {
          this.$notify({
            title: "Server error",
            text: "Server is currently off. Please try again later...",
            type: "error"
          });
        } else if (error.response.status === 500) {
          this.$notify({
            title: "Internal Server Error",
            text: "Something went wrong on the server! Please try again later...",
            position: "bottom right",
            type: "error"
          })
        }
      })
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
      if (!this.isDurationEntered) {
        this.durationIsEntered = false;
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
      if (!this.isPriceEntered) {
        this.priceIsEntered = false;
        return false;
      }
      if (!this.isCapacityEntered) {
        this.capacityIsEntered = false;
        return false;
      }
      if (!this.isCancellationFeeEntered) {
        this.cancellationFeeIsEntered = false;
        return false;
      }
      if (!this.isPhotoEntered) {
        this.photoIsEntered = false;
        return false;
      }
      return true;
    },
    async isDataCorrect() {
      this.addressIsValid = false;
      this.validateAddress();
    },
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
    validateAddress() {
      const apiKey = 'VrDrl5BjEA0Whvb-chHbFz96HV4qlCXB-yoiTRRLKno';
      const url = 'https://geocoder.ls.hereapi.com/6.2/geocode.json' +
          '?apiKey=' + apiKey +
          '&housenumber=' + this.adventure.address.number +
          '&street=' + this.adventure.address.street +
          '&city=' + this.adventure.address.city +
          '&country=' + this.adventure.address.country;
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
              this.adventure.address.city = this.transliterate(address.City);
              this.adventure.address.country = this.transliterate(address.AdditionalData[0].value);
              this.adventure.address.street = this.transliterate(address.Street);
              this.adventure.address.longitude = location.Longitude;
              this.adventure.address.latitude = location.Latitude;
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
    },
    addPhoto(e) {
      e.target.files.forEach(photo => {
        this.photos.push(photo);
        this.imageUrls.push(URL.createObjectURL(photo));
        this.photoIsEntered = true;
      })
    },
    removePhoto(index) {
      this.photos.splice(index, 1);
      this.imageUrls.splice(index, 1);
    },
    addEquipment() {
      if (this.equipmentPiece.name.trim() !== "") {
        if (this.adventure.adventureEquipment.indexOf(this.equipmentPiece.name.trim()) === -1) {
          this.adventure.adventureEquipment.push(this.equipmentPiece);
          this.equipmentPiece = {name: ""};
          this.equipmentAlreadyExists=false;
        }
        else {
          this.equipmentAlreadyExists=true;
        }
      }
    },
    removeEquipment(equipmentName) {
      this.adventure.adventureEquipment.splice(this.adventure.adventureEquipment.indexOf(equipmentName), 1);
    },
    addService() {
      if (this.additionalService.trim() !== "") {
        let newService = {};
        newService["name"] = this.additionalService.trim();
        newService["price"] = this.additionalServicePrice===null?0:this.additionalServicePrice;
        if (this.adventure.additionalServices.indexOf(newService) === -1) {
          this.adventure.additionalServices.push(newService);
          this.additionalService = "";
          this.additionalServicePrice = null;
          this.serviceAlreadyExists = false;
        } else {
          this.serviceAlreadyExists = true;
        }
      }
    },
    removeService(service) {
      this.adventure.additionalServices.splice(this.adventure.additionalServices.indexOf(service), 1);
    },
    addRule() {
      if (this.ruleText.trim() !== "" && this.ruleType.trim() !== "") {
        if (!this.ruleExists(this.ruleText.trim())) {
          let newRule = {};
          newRule["type"] = this.ruleType;
          newRule["rule"] = this.ruleText;
          this.adventure.conductRules.push(newRule);
          this.ruleType = "";
          this.ruleText = "";
          this.ruleIsValid = true;
          this.ruleAlreadyExists = false;
        } else {
          this.ruleAlreadyExists = true;
        }
      } else {
        this.ruleIsValid = false;
      }
    },
    removeRule(rule) {
      this.adventure.conductRules.splice(this.getRuleIndex(rule), 1);
    },
    getRuleIndex(rule) {
      let index = -1;
      this.adventure.conductRules.forEach(conductRule => {
        if (conductRule.rule === rule) {
          index = this.adventure.conductRules.indexOf(conductRule);
        }
      })
      return index;
    },
    positiveRules(rulesOfConduct) {
      let positiveRules = [];
      rulesOfConduct.forEach(rule => {
        if (rule.type === "Do") {
          positiveRules.push(rule.rule);
        }
      });
      return positiveRules;
    },
    negativeRules(rulesOfConduct) {
      let negativeRules = [];
      rulesOfConduct.forEach(rule => {
        if (rule.type === "DoNot") {
          negativeRules.push(rule.rule);
        }
      });
      return negativeRules;
    },
    ruleExists(rule) {
      return this.getRuleIndex(rule) !== -1;
    }
  }
}
</script>

<style scoped>

  .upload-button {
    display: none;
  }

  label, .tag-container, small {
    max-width: 400px;
  }

  .equipment-tag {
    background-color: #008970;
    font-weight: bold;
    color: white;
    border: 2px solid #008970;
    width: fit-content;
    font-size: 12px;
  }

  .equipment-tag:hover   {
    background-color: white;
    color: #008970;
    border: 2px solid #008970;
    width: fit-content;
    font-size: 12px;
  }

  .equipment-tag:hover .tag-button {
    background-color: transparent;
    color: #008970;
    border: none;
  }

  .tag-button {
    background-color: transparent;
    color: white;
    border: none;
  }

  textarea {
    resize: none;
    width: 100%;
    text-align: justify;
    max-width: 400px;
  }

  input {
    max-width: 400px;
  }

  input::placeholder {
    color: grey;
  }

  h3 {
    text-align: center;
  }

  h4 {
    text-align: center;
  }

  p {
    color: #e23c52;
  }
</style>
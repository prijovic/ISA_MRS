<template>
  <div class="row">
    <div class="col-2"></div>
    <div class="col-8 pt-5 mb-5">
      <div class="container px-4 py-3 rounded form" spellcheck="false" >
        <div class="container-fluid">
          <h3>New Adventure</h3>
          <div class="row main justify-content-center">
            <div class="row main">
              <div class="col main">
                <div class="row">
                  <div class="col-4"></div>
                  <div class="col-6">

                    <div class="row">
                      <label for="name">Name</label>
                      <input class="form-control" type="text" id="name" placeholder="E.g. Adventure" @input="nameIsEntered=true">
                      <p v-if='!nameIsEntered'>'Name' is a mandatory field.</p>
                    </div>

                    <div class="row">
                      <label for="description">Description</label>
                      <textarea  class="form-control" rows="10" maxlength="500" placeholder="Please describe why you want to join our community in 50-500 characters." id="description" @input="descriptionIsEntered=true"></textarea>
                    </div>

                    <div class="row mt-5">
                      <label for="equipment">Equipment</label>
                      <input class="form-control" v-model="equipmentPiece" type="text" id="equipment" placeholder="E.g. Fishing rod" @keyup.enter="addEquipment">
                      <small class="form-text text-muted">
                        Press Enter to add equipment
                      </small>
                      <div class="row">
                        <div class="equipment-tag rounded-pill mt-1 ms-1 p-1" v-for="(pieceOfEquipment, index) in equipment" :key="index">
                          {{pieceOfEquipment}}
                          <button class="tag-button text-center" @click="removeEquipment(pieceOfEquipment)"><font-awesome-icon icon="x" style="width: 9px; height: 9px"></font-awesome-icon></button>
                        </div>
                      </div>
                    </div>

                    <div class="row mt-5">
                      <div class="d-flex d-inline-flex justify-content-between p-0">
                        <label for="additionalServices" class="ms-2">Additional Services</label>
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
                      <div class="row" id="additionalServices">
                        <div class="equipment-tag rounded-pill mt-1 ms-1 p-1" v-for="(additionalService, index) in additionalServices" :key="index">
                          {{additionalService.name + " " + (additionalService.price===null?"Free":additionalService.price + "$")}}
                          <button class="tag-button text-center" @click="removeService(additionalService)"><font-awesome-icon icon="x" style="width: 9px; height: 9px"></font-awesome-icon></button>
                        </div>
                      </div>
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
                      <input  class="form-control" type="text" id="country" placeholder="Country">
                    </div>
                    <div class="row">
                      <label for="city">City</label>
                      <input class="form-control" type="text" id="city" placeholder="City">
                    </div>
                    <div class="row">
                      <label for="street">Street</label>
                      <input class="form-control" type="text" id="street" placeholder="Street">
                    </div>
                    <div class="row">
                      <label for="number">Number</label>
                      <input class="form-control"  type="text" id="number" placeholder="House Number">
                    </div>
                    <div class="row mt-5">
                      <label for="price">Price</label>
                      <input type="number" step="any" min="0"  id="price" class="form-control" placeholder="Price in $">
                    </div>
                    <div class="row">
                      <label for="capacity">Capacity</label>
                      <input type="number" step="any" min="0"  id="capacity" class="form-control" placeholder="Max people">
                    </div>
                    <div class="row">
                      <label for="cancellation">Cancellation fee</label>
                      <input type="number" step="any" min="0" max="100"  id="cancellation" class="form-control" placeholder="Cancellation fee in %">
                    </div>

                    <div class="row mt-5">
                      <div class="d-flex d-inline-flex justify-content-between p-0">
                        <label for="rulesTable" class="ms-2">Rules of Conduct</label>
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
                      <table class="mt-1" id="rulesTable">
                        <thead style="background-color: transparent">
                        <tr>
                          <th style="background-color: #008970; color: #f7f7f2; ; border-bottom: #008970 1px solid">Do</th>
                          <th style="background-color: #e23c52; color: #f7f7f2; border-left: #008970 1px solid; border-bottom: #e23c52 1px solid">Don't</th>
                        </tr>
                        </thead>
                        <tbody>
                        <tr v-for="(rule, index) in rulesOfConduct" :key="index" :class="index%2!==0?'odd':'even'">
                          <td style="border-right: #008970 1px solid">
                            <div class="d-flex d-inline-flex justify-content-between p-0">
                              <div>{{rule.Do}}</div><button v-if="rule.Do" class="button" style="border: none; background-color: transparent; color: #e23c52" @click="removeRule(rule.Do)"><font-awesome-icon icon="minus"></font-awesome-icon></button>
                            </div>
                          </td>
                          <td style="border-right: #008970 1px solid">
                            <div class="d-flex d-inline-flex justify-content-between p-0">
                              <div>{{rule.DoNot}}</div><button v-if="rule.DoNot" class="button" style="border: none; background-color: transparent; color: #e23c52" @click="removeRule(rule.DoNot)"><font-awesome-icon icon="minus"></font-awesome-icon></button>                            </div>
                          </td>
                        </tr>
                        </tbody>
                      </table>
                      <p v-if='!ruleIsValid'>Cannot add empty rule.</p>
                      <p v-if='ruleAlreadyExists'>Rule already exists.</p>
                    </div>
                  </div>
                  <div class="col-4">
                  </div>
                </div>
              </div>
            </div>
          </div>
          <div class="d-flex pt-3 justify-content-center">
<!--            <router-link v-if="isAdmin" to="/admin/users" class="btn btn-red mt-3 me-1">Cancel</router-link>-->
<!--            <button type="button" class="btn mt-3" @click="submit">Submit</button>-->
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
import {faPlus, faMinus, faX} from "@fortawesome/free-solid-svg-icons";

library.add(faPlus, faMinus, faX);

export default {
  name: "AdventureCreationPage",
  components: {FontAwesomeIcon},
  data() {
    return {
      equipmentPiece: "",
      equipment: [],
      additionalService: "",
      additionalServicePrice: null,
      additionalServices: [],
      conductRules: [],
      ruleType: "",
      ruleText: "",
      ruleIsValid: true,
      ruleAlreadyExists: false,
      nameIsEntered: true,
      descriptionIsEntered: true,
    }
  },
  computed: {
    rulesOfConduct() {
      let pairsOfRules = [];
      let positiveRules = this.positiveRules(this.conductRules);
      let negativeRules = this.negativeRules(this.conductRules);
      let size = Math.max(positiveRules.length, negativeRules.length);
      for (let i = 0; i < size; i++) {
        pairsOfRules.push({
          "Do": i + 1 <= positiveRules.length ? positiveRules[i] : "",
          "DoNot": i + 1 <= negativeRules.length ? negativeRules[i] : ""
        });
      }
      return pairsOfRules;
    }
  },
  methods: {
    addEquipment() {
      if (this.equipmentPiece.trim() !== "") {
        this.equipment.push(this.equipmentPiece);
        this.equipmentPiece = "";
      }
    },
    removeEquipment(equipmentName) {
      this.equipment.splice(this.equipment.indexOf(equipmentName), 1);
    },
    addService() {
      if (this.additionalService.trim() !== "") {
        let newService = {};
        newService["name"] = this.additionalService;
        newService["price"] = this.additionalServicePrice;
        if (this.additionalServices.indexOf(newService) === -1) {
          this.additionalServices.push(newService);
          this.additionalService = "";
          this.additionalServicePrice = null;
        }
      }
    },
    removeService(service) {
      this.additionalServices.splice(this.additionalServices.indexOf(service), 1);
    },
    addRule() {
      if (this.ruleText.trim() !== "" && this.ruleType.trim() !== "") {
        if (!this.ruleExists(this.ruleText)) {
          let newRule = {};
          newRule["type"] = this.ruleType;
          newRule["rule"] = this.ruleText;
          this.conductRules.push(newRule);
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
      this.conductRules.splice(this.getRuleIndex(rule), 1);
    },
    getRuleIndex(rule) {
      let index = -1;
      this.conductRules.forEach(conductRule => {
        if (conductRule.rule === rule) {
          index = this.conductRules.indexOf(conductRule);
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
      let exists = false;
      this.conductRules.forEach(conductRule => {
        if (conductRule.rule === rule) {
          exists = true;
        }
      })
      return exists;
    }
  }
}
</script>

<style scoped>
  .equipment-tag {
    background-color: #008970;
    color: #99EEDF;
    width: fit-content;
    font-size: 12px;
  }

  .equipment-tag:hover   {
    background-color: #99EEDF;
    color: #008970;
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
    color: #99EEDF;
    border: none;
  }

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

  h4 {
    text-align: center;
  }

  p {
    color: #e23c52;
  }
</style>
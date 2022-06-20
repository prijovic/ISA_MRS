<template>
  <div class="row">
    <div class="col-2"></div>
    <div class="col-8 pt-5 mb-5">
      <div class="container px-4 py-3 rounded form" spellcheck="false" >
        <div class="container-fluid">
          <h3>User Category</h3>
          <div class="row main justify-content-center">
            <div class="row main">
              <div class="col-2"></div>
              <div class="col-8">

                <div class="row justify-content-center">
                  <div class="row justify-content-center">
                    <label for="name">Name</label>
                  </div>
                  <input class="form-control" v-model="category.name" type="text" id="name" placeholder="E.g. Category" @input="nameIsEntered=true">
                  <p v-if='!nameIsEntered'>'Name' is a mandatory field.</p>
                </div>
                <div class="row justify-content-center">
                  <div class="row justify-content-center">
                    <label for="price">Required Points</label>
                  </div>
                  <input v-model="category.requiredPoints" type="number" step="any" min="0"  id="price" class="form-control" placeholder="Number of required points" @input="pointsAreEntered=true">
                  <p v-if='!pointsAreEntered'>'Required Points' is a mandatory field.</p>
                </div>
                <div class="row justify-content-center">
                  <div class="row justify-content-center">
                    <label for="client">Client Discount</label>
                  </div>
                  <input v-model="category.clientDiscount" type="number" step="any" min="0" max="100"  id="client" class="form-control" placeholder="Client's discount in %" @input="discountIsEntered=true">
                  <p v-if='!discountIsEntered'>'Client Discount' is a mandatory field.</p>
                </div>
                <div class="row justify-content-center">
                  <div class="row justify-content-center">
                    <label for="owner">Owner Bonus</label>
                  </div>
                  <input v-model="category.ownerBenefit" type="number" step="any" min="0" max="100"  id="owner" class="form-control" placeholder="Owner's bonus in %" @input="bonusIsEntered=true">
                  <p v-if='!bonusIsEntered'>'Owner Bonus' is a mandatory field.</p>
                </div>

                <div class="row justify-content-center mt-4">
                  <div class="row justify-content-center">
                    <label for="imageInput">Icon</label>
                    <p v-if='!photoIsEntered'>Icon is mandatory.</p>
                  </div>
                  <div class="row p-0 justify-content-center" id="imageInput" style="max-width: 400px;min-width: 120px">
                    <label class="imageUploadLabel text-center rounded m-1" style="background-color:white;display: table;width: 50px;height: 50px;border: 1px solid #008970;cursor: pointer">
                      <span style="display:table-cell; vertical-align: middle">
                        <font-awesome-icon class="rounded-circle" icon="plus-circle" style="color: #008970; background-color: transparent;height: 50px"></font-awesome-icon>
                        <input class="upload-button" type="file" accept="image/jpeg" ref="photo" @change="addPhoto">
                      </span>
                    </label>
                    <div v-if="imageUrl!==null&&imageUrl!==undefined" class="m-1 rounded" style="width:50px;height:50px;border: 1px solid #008970;background-color: lightgray;position: relative;text-align: center">
                      <img :src="imageUrl" alt="" style="width:100%; height:100%; object-fit: contain;">
                    </div>
                  </div>
                </div>

              </div>
              <div class="col-2"></div>
            </div>
          </div>
          <div class="d-flex pt-3 justify-content-center">
            <router-link to="/admin/loyaltyProgram" class="btn btn-red mt-3 me-1">Cancel</router-link>
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
import {faPlusCircle} from "@fortawesome/free-solid-svg-icons";
import axios from "axios";
import {toggleProcessing} from "@/components/state";

library.add(faPlusCircle);

export default {
  name: "LoyaltyCategoryCreationPage",
  components: {FontAwesomeIcon},
  data() {
    return {
      category: {
        name: null,
        requiredPoints: null,
        clientDiscount: null,
        ownerBenefit: null,
        icon: null
      },
      nameIsEntered: true,
      pointsAreEntered: true,
      discountIsEntered: true,
      bonusIsEntered: true,
      imageUrl: null,
      photoIsEntered: true,
      photo: null
    }
  },
  methods: {
    addPhoto(e) {
      this.photo = e.target.files[0];
      this.imageUrl = URL.createObjectURL(this.photo);
      this.photoIsEntered = true;
    },
    submit() {
      if (this.isDataEntered()) {
        toggleProcessing();
        let category = this.category
        axios.post("/LoyaltyProgram/addCategory", category, {
          headers: {
            Authorization: "Bearer " + this.$store.getters.access_token,
          }
        })
        .then((category) => {
            let formData = new FormData();
            formData.append("file", this.photo);
            axios.post("/Photos/upload", formData, {
              headers: {
                Authorization: "Bearer " + this.$store.getters.access_token,
                "Content-type": "multipart/form-data"
              },
            })
            .then((response) => {
              let dto = {id: category.data.id, photos: [response.data]}
              axios.post("/LoyaltyProgram/connectPhotoToCategory", dto,{
                headers: {
                  Authorization: "Bearer " + this.$store.getters.access_token,
                },
              }).then(() =>{
                this.$notify( {
                  title: "Successful adding",
                  text: "You have successfully added a new category.",
                  position: "bottom right",
                  type: "success"
                });
                toggleProcessing();
              })
            })
          }
        )
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
          toggleProcessing();
        })
      }
    },
    isDataEntered() {
      if (this.category.name === null) {
        this.nameIsEntered = false;
        return false;
      }
      if (this.category.requiredPoints === null) {
        this.pointsAreEntered = false;
        return false
      }
      if (this.category.clientDiscount === null) {
        this.discountIsEntered = false;
        return false
      }
      if (this.category.ownerBenefit === null) {
        this.bonusIsEntered = false;
        return false
      }
      if (this.photo === null) {
        this.photoIsEntered = false;
        return false
      }
      return true;
    }
  }
}
</script>

<style scoped>
.upload-button {
  display: none;
}

label, small {
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
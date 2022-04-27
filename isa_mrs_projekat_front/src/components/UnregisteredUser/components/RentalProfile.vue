<template>
    <div v-if="rentalObject" class="container-fluid mt-2 mb-3">
        <div class="row align-items-start">
            <div class="col-md-7 p-3">
              <ImageSlider :photos="this.rentalObject.photos"/>
            </div>
            <div class="col-md-5 p-3">
<!--------------------------------------------------------------------------------------------------------------------->
                <div class="row">
                    <div class="rentalObjectName align-items-center">
                        <hr><h1 class="display-3 mb-2">{{ this.rentalObject.name }}</h1><hr>
                    </div>
                </div>

                <div v-if="rentalObjectType === 'VacationRental'" class="row main">
                  <div class="col main d-flex justify-content-center" title="Capacity">
                    <p class="display-5">
                      <i class="bi bi-people-fill me-1"></i><strong>{{  }}</strong>
                    </p>
                  </div>
                  <div class="col main d-flex justify-content-center" title="Number of rooms">
                    <p class="display-5">
                      <i class="bi bi-door-open-fill me-1"></i><strong>{{  }}</strong>
                    </p>
                  </div>
                  <div class="col main d-flex justify-content-center" title="Beds per room: 2 + 2 + 1">
                    <p class="display-5">
                      <i class="fa-solid fa-bed me-1"></i><strong>{{  }}</strong>
                    </p>
                  </div>
                </div>
              
                <div v-if="rentalObjectType === 'VacationRental'" class="row mb-3 mt-2">
                    <div class="col d-flex justify-content-center">
                        <p class="h4">
                            <i class="bi bi-clock-fill me-1"></i>{{ "Check in: " + this.rentalObject.checkIn }}
                        </p>
                    </div>
                    <div class="col d-flex justify-content-center">
                        <p class="h4">
                           <i class="bi bi-clock-fill me-1"></i>{{ "Check out: " + this.rentalObject.checkOut }}
                        </p>
                    </div>
                </div>
                <div v-if="rentalObjectType === 'Boat' || rentalObjectType === 'Adventure'" class="p-1" style="text-align: center;">
                    <p class="h4">
                       <i class="bi bi-people-fill me-1"></i>{{"Capacity: " + this.rentalObject.capacity}}
                    </p>
                </div>
                <div class="row">
                    <div class="d-flex justify-content-center align-items-center">
                        <div class="Stars" style="--rating: 0;"></div>
                        (0.0)
                    </div>
                </div>
                <div class="row mt-3 ps-1">
                   <p class="h5">Owner: <i><a href="">{{ this.rentalObject.rentalObjectOwner.name + " " + this.rentalObject.rentalObjectOwner.surname}}</a></i></p>
                </div>
                <div v-if="rentalObjectType === 'Boat'" class="border" style="font-weight: 700;">
                    <div class="row ps-1">
                       <div class="col">{{"Type: " + this.rentalObject.type}}</div>
                       <div class="col">{{ "Length: " + this.rentalObject.length }}</div>
                    </div>
                    <div class="row ps-1">
                       <div class="col">{{ "Engine number: " + this.rentalObject.engineNumber }}</div>
                       <div class="col">{{ "Engine power:" + this.rentalObject.enginePower }}</div>
                    </div>
                    <div class="row ps-1">
                       <div class="col">{{ "Maximum speed: " + this.rentalObject.maxSpeed }}</div>
                    </div>
                </div>
                <div class="row mt-3 ps-1" style="text-align: center;">
                    <p class="h3">
                        <i><strong>{{ "Price: $" + this.rentalObject.price }}</strong></i>
                    </p>
                </div>

<!--------------------------------------------------------------------------------------------------------------------->
            </div>
        </div>
        <div class="row align-items-start">
            <div class="col-md-7 ps-3">
                <RentalDescription :description="this.rentalObject.description"/>
                <RentalTags :additional-services="this.rentalObject.additionalServices"/>
                <RentalRules :conduct-rules="this.rentalObject.conductRules"/>
            </div>

            <div class="col-md-5">
              <div v-if="rentalObjectType === 'Boat'" class="row main mb-3">
                <div class="lineTitle">
                  <p class="h4">Boat info</p>
                  <hr class="ms-1">
                </div>
                <div class="row-fluid main px-5" style="font-weight: 700;">
                  <div class="row mt-3 main">
                    <div class="col main border">{{"Type: "  }}</div>
                    <div class="col main border">{{"Length: "  }}</div>
                  </div>
                  <div class="row main">
                    <div class="col main border">{{"Engine number: "  }}</div>
                    <div class="col main border">{{"Engine power: "  }}</div>
                  </div>
                  <div class="row main text-center">
                    <div class="col main border">{{"Maximum speed: "  }}</div>
                  </div>
                </div>
              </div>
              <div v-if="rentalObjectType === 'Boat'" class="row main">
                <div class="lineTitle">
                  <p class="h4">Navigation equipment</p>
                  <hr class="ms-1">
                </div>
                <p class="" style="color: grey;"></p>
              </div>
              <div v-if="rentalObjectType === 'Boat'" class="row main">
                <div class="lineTitle">
                  <p class="h4">Fishing equipment</p>
                  <hr class="ms-1">
                </div>
                <p class="" style="color: grey;"></p>
              </div>
              <div v-if="rentalObjectType === 'Adventure'" class="row main">
                <div class="lineTitle">
                  <p class="h4">Adventure equipment</p>
                  <hr class="ms-1">
                </div>
                <p class="" style="color: grey;"></p>
              </div>


              <RentalAddress :address="this.rentalObject.address.name"/>
            </div>

        </div>
        <div class="row align-items-start mt-4">
            <div class="col-md-7">
                <!--reviews-->
            </div>
        </div>
    </div>
</template>

<script>
import ImageSlider from "@/components/UnregisteredUser/components/ImageSlider";
import RentalDescription from "@/components/UnregisteredUser/components/RentalDescription";
import RentalTags from "@/components/UnregisteredUser/components/RentalTags";
import RentalAddress from "@/components/UnregisteredUser/components/RentalAddress";
import RentalRules from "@/components/UnregisteredUser/components/RentalRules";
import axios from "axios/index";

export default {
  name: "RentalProfile",
  components: {RentalRules, RentalAddress, RentalTags, RentalDescription, ImageSlider},
  props: {
    rentalObjectId: { type: Number, required: true },
    rentalObjectType: { type: String, required: true }
  },
  data() {
    return {
      rentalObject: null
    }
  },
  async setup() {
    if(this.rentalObjectType === "Boat") {
        this.rentalObject = await axios.get("/RentalObjects/getBoat", {
          headers: {
            Authorization: "Bearer " + this.$store.getters.access_token
          }
        });
    }
    else if(this.rentalObjectType === "Adventure") {
      this.rentalObject = await axios.get("/RentalObjects/getAdventure", {
        headers: {
          Authorization: "Bearer " + this.$store.getters.access_token
        }
      });
    }
    else {
      this.rentalObject = await axios.get("/RentalObjects/getVacationRental", {
        headers: {
          Authorization: "Bearer " + this.$store.getters.access_token
        }
      });
    }
  }
}
</script>

<style scoped>
  div.rentalObjectName {
    display: flex;
  }

  :root {
    --star-size: 60px;
    --star-color: black;
    --star-background: blue;
  }

  .Stars {
    --percent: calc(var(--rating) / 5 * 100%);
    display: inline-block;
    font-size: var(--star-size);
    font-family: Times,serif;
    line-height: 1;
  }

  .Stars::before {
    content: '★★★★★';
    background: linear-gradient(90deg, var(--star-background) var(--percent), var(--star-color) var(--percent));
    -webkit-background-clip: text;
    -webkit-text-fill-color: transparent;
  }
</style>
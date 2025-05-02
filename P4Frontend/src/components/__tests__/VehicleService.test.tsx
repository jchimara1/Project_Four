import { describe, it, expect, beforeAll, afterEach, afterAll } from "vitest";
import { setupServer } from "msw/node";
import {http, HttpResponse} from "msw";
import axios from "axios";

// import * as VehicleService from "../../frontend_service/VehicleService.ts"
import { Vehicle } from "../../types/Vehicle.ts";
import {create} from "../../frontend_service/VehicleService.ts";

describe("VehicleService.create", () => {
axios.defaults.baseURL = "http://localhost:3000";

const server = setupServer();

beforeAll(() => server.listen({ onUnhandledRequest: 'error' }));
afterEach(() => server.resetHandlers());
afterAll(() => server.close());

    it("posts a vehicle and returns it with an id", async () => {
        const input: Vehicle = {
            id: 1,
            make: "Toyota",
            model: "Corolla",
            year: 2018,
            price: 15000,
            used: true,
        };

        server.use(
            http.post("/api/vehicle", () => {
                console.log("MSW: request received");
                return HttpResponse.json(input, {status: 201})
            })
        );

        expect(await create(input)).toStrictEqual(input);

    });
});

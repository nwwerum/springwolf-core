/* SPDX-License-Identifier: Apache-2.0 */
import { SchemasComponent } from "./schemas.component";
import { AsyncApiService } from "../../service/asyncapi/asyncapi.service";
import { MatAccordion } from "@angular/material/expansion";
import { render, screen } from "@testing-library/angular";
import { JsonComponent } from "../json/json.component";
import { Observable } from "rxjs";
import { AsyncApi } from "../../models/asyncapi.model";
import { of } from "rxjs/internal/observable/of";
import { Server } from "../../models/server.model";
import { Schema } from "../../models/schema.model";
import { initInfo } from "../../service/mock/init-values";
import { SchemaComponent } from "../schema/schema.component";
import { RangeComponent } from "../schema/range/range.component";

describe("SchemasNewComponent", () => {
  const mockedAsyncApiService: { getAsyncApi: () => Observable<AsyncApi> } = {
    getAsyncApi: () =>
      of({
        info: initInfo,
        servers: new Map<string, Server>(),
        channels: [],
        channelOperations: [],
        components: {
          schemas: new Map<string, Schema>(),
        },
        defaultContentType: "application/json",
      }),
  };

  beforeEach(async () => {
    await render(SchemasComponent, {
      declarations: [SchemaComponent, RangeComponent, JsonComponent],
      imports: [MatAccordion],
      providers: [
        { provide: AsyncApiService, useValue: mockedAsyncApiService },
      ],
    });
  });

  it("should create the component", () => {
    expect(screen).toBeTruthy();
  });
});

## Description

The **Send Message Template (Whatsapp)** node allows you to send **pre-approved message templates** to users on WhatsApp using the Meta Cloud API.

This is used when you want to send structured messages (e.g., confirmations, updates, alerts) that are approved in advance by Meta. Templates can include headers, body parameters, and optional flows.

To use this node, you must first create your template on the [Meta for Developers Portal](https://developers.facebook.com/). Navigate to **WhatsApp > Message Templates**, select your business number, and create a template. Templates should define placeholders (variables), if needed, and their **variable type must be set as `number`**, not name-based keys.

---

## Node Type

`whatsapp_send_template`

---

## Inputs

| Name                  | Data Type | Type                  | Description                                                                                                                                 |
|-----------------------|-----------|------------------------|---------------------------------------------------------------------------------------------------------------------------------------------|
| `template_name`       | String    | Constant               | The exact name of the template as defined in Meta. Must match character-for-character.                                                     |
| `template_language`   | String    | Constant               | Language code of the template (e.g., `en`, `en_US`, etc.). Should match what was set on the Meta platform.                                 |
| `template_header`     | String    | Constant (Optional)    | Currently supports only **Text** as header type. Select this only if the template includes a text header. Leave empty otherwise.           |
| `header_variable`     | String    | Reference / Constant   | Value for the header variable (if any). Can be either a **dynamic variable** or **static text**. Leave empty if the header has no variable.|
| `body_variable`       | List      | Reference              | Provide one or more variables to fill in the dynamic placeholders in the template body. The sequence **must match** that of the template. |
| `flow_present`        | Boolean   | Constant               | Select **Yes** if the template contains a flow component (e.g., quick replies), else select **No**.                                        |
| `whatsapp_config`     | String    | Reference              | Reference to the WhatsApp global configuration object. This is required for authentication and delivery via the Meta Cloud API.            |

> Note: Ensure that the values selected in `body_variable` match the **exact sequence** of variables as they were defined in the Meta template. The selected sequence is visible as chips once selected.

![Example Input Configuration](/appz-svc/product-guide/image/348)

---

## Configuration

This node uses credentials from the platform’s **WhatsApp global configuration**, which includes:

- WhatsApp Business Account access token
- Sender number ID (business phone number)

Please ensure the appropriate global config (`whatsapp_global_config`) is created in the platform's **Global Configurations** section before using this node.

---

## Outputs

This node does **not produce any outputs**. It simply performs the action of **sending a template message** via WhatsApp.

---

## Example Use Cases

- Sending order confirmation messages with dynamic order ID and total.
- Notifying users about appointment schedules.
- Triggering alerts (e.g., transaction success) using rich, structured templates.

---

## Notes

- Only templates approved on Meta’s platform can be used.
- Variable types on the Meta platform should be **numerically indexed**, not named.
- Leave optional fields empty if the corresponding components weren’t part of the template.
- WhatsApp global configuration is mandatory for message delivery.

---

## Related Documents

- Global Configuration Guide
- Send Message (WhatsApp) Node
- [Meta Template Message Guide](https://developers.facebook.com/docs/whatsapp/message-templates/)

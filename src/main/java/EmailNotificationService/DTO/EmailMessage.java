package EmailNotificationService.DTO;

public record EmailMessage(
        String to,
        String subject,
        String body
) {
}

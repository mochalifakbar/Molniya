pub fn parse_packet(packet: &[u8]) -> usize {
    packet.len()
}

pub fn normalize_host(input: &str) -> Result<String, String> {
    if input.is_empty() {
        return Err("Error: input kotlin kosong".to_string());
    }

    let normalized = input.trim().to_ascii_lowercase();
    Ok(normalized)
}

#[uniffi::export]
pub fn native_version() -> String {
    env!("CARGO_PKG_VERSION").to_owned()
}

uniffi::setup_scaffolding!();

#[derive(Debug, uniffi::Error)]
pub enum ParseError {
    InvalidFormat { detail: String },
    MissingPort { detail: String },
}

impl std::fmt::Display for ParseError {
    fn fmt(&self, f: &mut std::fmt::Formatter<'_>) -> std::fmt::Result {
        match self {
            ParseError::InvalidFormat { detail } => {
                write!(f, "salah format bos: {}", detail)
            }

            ParseError::MissingPort { detail } => {
                write!(f, "port kosong ya bos: {}", detail)
            }
        }
    }
}

impl std::error::Error for ParseError {}

#[uniffi::export]
pub fn parse_target(input: String) -> Result<String, ParseError> {
    if input.is_empty() {
        return Err(ParseError::InvalidFormat {
            detail: "Input kosong".to_string(),
        });
    }
    let (host, port) = input.split_once(':').ok_or(ParseError::InvalidFormat {
        detail: format!("Format input:{} salah bos, harusnya host:port", input),
    })?;

    if port.trim().is_empty() {
        return Err(ParseError::MissingPort {
            detail: "Port kosong".to_string(),
        });
    }

    Ok(format!(
        "Host valid bos, ini hostnya {}, port juga valid ya bos: {}",
        host, port
    ))
}

#[uniffi::export(callback_interface)]
pub trait AppCallback: Send + Sync {
    fn on_event(&self, message: String);
}

#[uniffi::export]
pub fn trigger_callback(callback: Box<dyn AppCallback>) {
    callback.on_event("Sinyal dari native heap berhasil nembus UI kotlin ya bosss".to_string());
}

#[cfg(test)]
mod tests {
    use super::*;

    #[test]
    fn test_normalize_berhasil() {
        let hasil = normalize_host("EXaMple.CoM");
        assert_eq!(hasil.unwrap(), "example.com");
    }

    #[test]
    fn test_parse_packet() {
        let dummy_packet = [0u8, 1, 2, 3, 4];
        let ukuran = parse_packet(&dummy_packet);
        assert_eq!(ukuran, 5);
    }
}

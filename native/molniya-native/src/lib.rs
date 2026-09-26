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
